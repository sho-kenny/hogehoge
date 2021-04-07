import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: MainPage(),
      routes: <String, WidgetBuilder> {
        '/home': (BuildContext context) => MainPage(),  // 最初のページ
        '/subpage1': (BuildContext context) => SubPage1(),  // 次のページ
        '/subpage2': (BuildContext context) => SubPage2(),  // 最後のページ
      },
    );
  }
}

class MainPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Mainページ"),
      ),
      body: Container(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text("Main"),
              RaisedButton( // 立体的なボタン
                onPressed: () => Navigator.of(context).pushNamed("/subpage1"),  // 次の画面を乗せる
                child: Text("Sub1ページへ"),
              )
            ],
          ),
        ),
      ),
    );
  }
}

class SubPage1 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Sub1ページ"),
      ),
      body: Container(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text("Sub1"),
              RaisedButton( // 立体的なボタン
                onPressed: () => Navigator.of(context).pushNamed("/subpage2"),  // 次の画面を乗せる
                child: Text("Sub2ページへ"),
              ),
              RaisedButton(
                onPressed: () => Navigator.of(context).pop(),  // この画面を取り除く
                child: Text("戻る"),
              )
            ],
          ),
        ),
      ),
    );
  }
}

class SubPage2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Sub2ページ"),
      ),
      body: Container(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text("Sub2"),
              RaisedButton(
                onPressed: () => Navigator.of(context).pop(),  // この画面を取り除く
                child: Text("戻る"),
              )
            ],
          ),
        ),
      ),
    );
  }
}
