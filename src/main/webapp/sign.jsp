<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 27.09.2020
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignIn</title>
</head>
<body>
<h2>Sign in:</h2>
<form action="sign" method="post">
    <div>Login:<br>
        <input type="text" required name="login"/>
    </div>
    <div>Password:<br>
        <input type="password" required name="password"/>
    </div>
    <div>
        <input type="submit" value="Sign in"/>
    </div>
</form>
<h2>If you not registered yet, please follow on registration page:</h2>
<a href="http://localhost:8080/todo_list/registration">Registration now</a>
</body>
</html>
