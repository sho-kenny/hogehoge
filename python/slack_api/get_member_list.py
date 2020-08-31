import requests
import json
import csv


# slack��API�Ŏ擾����token���L��
TOKEN = "xoxp-632316587591-1023126913155-1313736708791-3e4e2015356a594e5e0d733e084539fd"
SLACK_API_BASE = "https://slack.com/api"
URL1 = "/conversations.members?token="


# �`�����l���ꗗ���擾
fetch_all_channels_url = "https://slack.com/api/conversations.list?token=" + TOKEN
response = requests.get(fetch_all_channels_url)
channel_hash = response.json()
channels = channel_hash["channels"]

# �A�[�J�C�u����Ă���`�����l���Ə��������o�[0�̃`�����l���͏��O
channels_under_100 = [channel for channel in channels 
                    if channel.get("is_archived") == False 
                    and channel.get("num_member") != 0 
                    and channel.get("num_members") <= 100]
channels_over_100 = [channel for channel in channels 
                   if channel.get("is_archived") == False 
                   and channel.get("num_member") != 0 
                   and channel.get("num_members") > 100]

# �����o�[�ꗗ���擾����API
fetch_all_members_url = SLACK_API_BASE + "/users.list?token=" + TOKEN
res = requests.get(fetch_all_members_url)
members_hash = res.json()
members = members_hash["members"]
# ���������o�[
members = [member for member in members 
           if member.get("deleted") == False 
           and member.get("real_name") != "Simple Poll"]
# �폜���������o�[
deleted_members = [member for member in members 
                   if member.get("deleted") == True]

# 100�ȉ��̃`�����l�������o�[���o��
create_member_list_under_100(channels_under_100)
# 100���߂̃`�����l�������o�[���o��
create_member_list_over_100(channels)


# 100�ȉ��̃`�����l���̃����o�[���X�g�擾���Acsv�o��
def create_member_list_under_100(channels):

    for channel in channels:
        # ����`�����l���ɓ����Ă��郆�[�U�[�ꗗ���擾
        fetch_members_in_channel_url = SLACK_API_BASE + URL1 + TOKEN + "&channel=" + channel.get("id")
        res = requests.get(fetch_members_in_channel_url)
        user_hash = res.json()
        user_list = []
        # ���X�g�쐬���\�b�h
        user_list = add_member(user_hash, members, user_list)
        
        # csv�o��
        create_csv(channel.get("name"), user_list)


# 100��葽���̃`�����l���̃����o�[���X�g�擾���Acsv�o��
def create_member_list_over_100(channels):
 
    for channel in channels:
        user_list = []
        fetch_members_in_channel_url = SLACK_API_BASE + URL1 + TOKEN + "&channel=" + channel.get("id")
        user_hash = {}
        # ����`�����l���ɓ����Ă��郆�[�U�[�ꗗ���擾
        for i in range(-(-channel.get("num_members") // 100)):
            if i == 0:
                # ����̎擾��next_cursor�͕K�v�Ȃ�
                res = requests.get(fetch_members_in_channel_url)
                user_hash = res.json()
                user_list = add_member(user_hash, members, user_list)
            else:
                # 100��葽�������o�[�̎擾��next_cursor���l��
                cursor_url = user_hash.get("response_metadata").get("next_cursor")
                next_url = fetch_members_in_channel_url + "&cursor=" + cursor_url
                res = requests.get(next_url)
                user_hash = res.json()
                user_list = add_member(user_hash, members, user_list)
    
        # csv�o��
        create_csv(channel.get("name"), user_list)


# ����`�����l���ɓ����Ă��郆�[�U�̃��X�g���쐬
def add_member(user_hash, members, user_list):

    user_ids = user_hash["members"]
    for id in user_ids:
        for member in members:
            if id in member.values():
                user_list.append(member)
    # �����ȉ��̋L�ڂ��ƍŌ�̗v�f�����ǉ��ł��Ȃ����B�ʓ|�������̂Ń_�T���������ł�����
    # user_list = [member.get("real_name") for member in members if id in member.values()]
    return user_list


# csv�o��
def create_csv(channel_name, user_list):
    
    csv_name = channel_name + "_MemberList.csv"

    with open(csv_name, "w", newline="") as f:
        writer = csv.writer(f)
        writer.writerow(["Channel", "ID", "Name"])
        for user in user_list:
            writer.writerow([channel_name, user.get("name"), user.get("real_name")])


