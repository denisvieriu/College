<%--
  Created by IntelliJ IDEA.
  User: dvieriu
  Date: 5/20/2018
  Time: 12:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<form action="LoginController" method="post">
    <div class="form-group">
        <label>Enter username : </label>
        <input class="form-control" type="text" name="user" placeholder="Enter username">
    </div>
    <div class="form-group">
        <label>Enter password : </label>
        <input class="form-control" type="password" name="pass" placeholder="Password">
    </div>
    <input type="submit" value="Login"  class="btn btn-primary">
</form>

</body>
</html>
