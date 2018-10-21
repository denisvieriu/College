<%@ page import="webubb.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: dvieriu
  Date: 5/19/2018
  Time: 7:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<%! User user; %>
<% user = (User) session.getAttribute("user");
    if (user != null)
        out.println("Welcome " + user.getUsername());
%>

<h1> Welcome ${username}</h1>

<form action="Logout">
    <input type="submit" value="Logout">
</form>
</body>
</html>
