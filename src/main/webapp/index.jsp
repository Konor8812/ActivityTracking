<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<c:if test="${empty sessionScope.regedAs}">
<form action="ActivityTracker?command=regUser" method="post" >
    Enter login    <input name="login"/> <br>
    Enter password <input name="password"/> <br>
    <input type="submit" value="register new user"/>
</form>
<hr>
<form action="ActivityTracker?command=logIn" method="post">
    Enter login    <input name="login"/> <br>
    Enter password <input name="password"/> <br>
    <input type="submit" value="log in!" />
</form>
<hr>
    <c:if test="${sessionScope.loginError == true}">
        <c:out value="login error: either pass or login are wrong" />
    </c:if>
</c:if>
<c:if test="${sessionScope.regError == true}">
    <c:out value="Registration error: user with such login exists!" />
</c:if>
<c:if test="${not empty sessionScope.regedAs}">
    <%
        if(session.getAttribute("regedAs").equals("user")) {
            response.sendRedirect("User.jsp");
        } else{
            response.sendRedirect("Admin.jsp");
        }
    %>
</c:if>
<c:if test="${sessionScope.userIsBlocked == true}">
    <c:out value="You are blocked by admin!"/>
</c:if>
</body>
</html>