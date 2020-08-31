import requests
import json
import csv


# slackのAPIで取得したtokenを記載
TOKEN = "xoxp-632316587591-1023126913155-1313736708791-3e4e2015356a594e5e0d733e084539fd"
SLACK_API_BASE = "https://slack.com/api"
URL1 = "/conversations.members?token="


# チャンネル一覧を取得
fetch_all_channels_url = "https://slack.com/api/conversations.list?token=" + TOKEN
response = requests.get(fetch_all_channels_url)
channel_hash = response.json()
channels = channel_hash["channels"]

# アーカイブされているチャンネルと所属メンバー0のチャンネルは除外
channels_under_100 = [channel for channel in channels 
                    if channel.get("is_archived") == False 
                    and channel.get("num_member") != 0 
                    and channel.get("num_members") <= 100]
channels_over_100 = [channel for channel in channels 
                   if channel.get("is_archived") == False 
                   and channel.get("num_member") != 0 
                   and channel.get("num_members") > 100]

# メンバー一覧を取得するAPI
fetch_all_members_url = SLACK_API_BASE + "/users.list?token=" + TOKEN
res = requests.get(fetch_all_members_url)
members_hash = res.json()
members = members_hash["members"]
# 現存メンバー
members = [member for member in members 
           if member.get("deleted") == False 
           and member.get("real_name") != "Simple Poll"]
# 削除したメンバー
deleted_members = [member for member in members 
                   if member.get("deleted") == True]

# 100以下のチャンネルメンバーを出力
create_member_list_under_100(channels_under_100)
# 100超過のチャンネルメンバーを出力
create_member_list_over_100(channels)


# 100以下のチャンネルのメンバーリスト取得し、csv出力
def create_member_list_under_100(channels):

    for channel in channels:
        # あるチャンネルに入っているユーザー一覧を取得
        fetch_members_in_channel_url = SLACK_API_BASE + URL1 + TOKEN + "&channel=" + channel.get("id")
        res = requests.get(fetch_members_in_channel_url)
        user_hash = res.json()
        user_list = []
        # リスト作成メソッド
        user_list = add_member(user_hash, members, user_list)
        
        # csv出力
        create_csv(channel.get("name"), user_list)


# 100より多いのチャンネルのメンバーリスト取得し、csv出力
def create_member_list_over_100(channels):
 
    for channel in channels:
        user_list = []
        fetch_members_in_channel_url = SLACK_API_BASE + URL1 + TOKEN + "&channel=" + channel.get("id")
        user_hash = {}
        # あるチャンネルに入っているユーザー一覧を取得
        for i in range(-(-channel.get("num_members") // 100)):
            if i == 0:
                # 初回の取得にnext_cursorは必要なし
                res = requests.get(fetch_members_in_channel_url)
                user_hash = res.json()
                user_list = add_member(user_hash, members, user_list)
            else:
                # 100より多いメンバーの取得にnext_cursorを考慮
                cursor_url = user_hash.get("response_metadata").get("next_cursor")
                next_url = fetch_members_in_channel_url + "&cursor=" + cursor_url
                res = requests.get(next_url)
                user_hash = res.json()
                user_list = add_member(user_hash, members, user_list)
    
        # csv出力
        create_csv(channel.get("name"), user_list)


# あるチャンネルに入っているユーザのリストを作成
def add_member(user_hash, members, user_list):

    user_ids = user_hash["members"]
    for id in user_ids:
        for member in members:
            if id in member.values():
                user_list.append(member)
    # 何か以下の記載だと最後の要素しか追加できなかた。面倒くさいのでダサい書き方でいいや
    # user_list = [member.get("real_name") for member in members if id in member.values()]
    return user_list


# csv出力
def create_csv(channel_name, user_list):
    
    csv_name = channel_name + "_MemberList.csv"

    with open(csv_name, "w", newline="") as f:
        writer = csv.writer(f)
        writer.writerow(["Channel", "ID", "Name"])
        for user in user_list:
            writer.writerow([channel_name, user.get("name"), user.get("real_name")])


