<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>

<form action="ActivityTracker?command=regUser" method="post" >
    <input name="login"/> <br>
    <input name="password"/> <br>
    <input type="submit" value="register new user"/>
</form>
<hr>
<form action="ActivityTracker?command=logIn" method="post">
    <input name="login"/> <br>
    <input name="password"/> <br>
    <input type="submit" value="log in!" />
</form>
<hr>
    <c:if test="${sessionScope.loginError == true}">
        <c:out value="login error: either pass or login are wrong" />
    </c:if>
</body>
</html>