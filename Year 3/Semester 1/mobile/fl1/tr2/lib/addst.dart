import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class AddData extends StatefulWidget {
  @override
  _AddDataState createState() => _AddDataState();
}

class _AddDataState extends State<AddData> {

  TextEditingController controllerName = new TextEditingController();
  TextEditingController controllerDescription = new TextEditingController();

  void addData(){
//    var url='http://10.0.2.2/my_startup/adddata.php';
//
//    http.post(url, body: {
//      "startupname": controllerName.text,
//      "startupdescription": controllerDescription.text
//    });
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("ADD DATA"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(10.0),
        child: ListView(
          children: <Widget>[
            new Column(
              children: <Widget>[
                new TextField(
                  controller: controllerName,
                  decoration: new InputDecoration(
                    hintText: "Startup idea",
                    labelText: "Startup idea",
                  ),
                ),
                new TextField(
                  controller: controllerDescription,
                  decoration: new InputDecoration(
                    hintText: "Startup idea",
                    labelText: "Startup description",
                  ),
                ),
                new Padding(
                  padding: const EdgeInsets.all(10.0),
                ),
                new RaisedButton(
                  child: new Text("ADD DATA"),
                  color: Colors.blueAccent,
                  onPressed: () {
                    addData();
                    Navigator.pop(context);
                  },
                )
              ],
            ),
          ],
        ),
      ),
    );
  }
}
