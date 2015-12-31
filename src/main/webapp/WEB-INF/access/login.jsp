<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login page</title>
</head>
<body>
<form method="post" action="j_spring_security_check">
    Name: <input id="username" type="text" name="j_username"/>
    Password: <input id="password" type="password" name="j_password"/>
    <button type="submit">Login</button>
</form>
<p class="message">${message}</p>
</body>
</html>