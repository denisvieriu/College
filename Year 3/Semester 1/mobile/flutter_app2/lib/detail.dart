import 'package:flutter/material.dart';

class Detail extends StatefulWidget {
  List list;
  int index;
  Detail({this.index, this.list});

  @override
  _DetailState createState() => _DetailState();
}

class _DetailState extends State<Detail> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("${widget.list[widget.index]['startup_name']}"),
      ),
      body: new Container(
        height: 250.0,
        padding: const EdgeInsets.all(20.0),
        child: new Card(
          child: new Card(
            child: new Center(
              child: new Column(
                children: <Widget>[
                  new Padding(
                    padding: const EdgeInsets.only(top: 30.0),
                  ),
                  new Text(
                    widget.list[widget.index]['startup_name'],
                    style: new TextStyle(fontSize: 20.0, fontWeight: FontWeight.bold),
                  ),
                  new Text(
                    "Description: ${widget.list[widget.index]['startup_description']}",
                    style: new TextStyle(fontSize: 15.0),
                  ),
                  new Padding(
                    padding: const EdgeInsets.only(top: 30.0),
                  ),
                  new Row(
                    mainAxisSize: MainAxisSize.min,
                    children: <Widget>[
                      new RaisedButton(
                        child: new Text("EDIT"),
                        color: Colors.green,
                        onPressed: () {},
                      ),
                      new RaisedButton(
                        child: new Text("DELETE"),
                        color: Colors.red,
                        onPressed: () {},
                      )
                    ],
                  )
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
