<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${empty sessionScope.language ? 'en' : sessionScope.language}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="loc"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="login"/></title>
</head>
<body>
<c:if test="${empty sessionScope.regedAs}">
<form action="ActivityTracker?command=regUser" method="post" >
    <fmt:message key="Enter.login"/>  <input name="login"/> <br>
    <fmt:message key="Enter.password"/> <input type="password" name="password"/> <br>
    <c:if test="${sessionScope.language == 'en'}">
    <input type="submit" value="register new user"/>
    </c:if>
    <c:if test="${sessionScope.language == 'ru'}">
    <input type="submit" value="зарегистрировать нового пользователя"/>
    </c:if>
</form>
<hr>
<form action="ActivityTracker?command=logIn" method="post">
    <fmt:message key="Enter.login"/>    <input name="login"/> <br>
    <fmt:message key="Enter.password"/> <input type="password" name="password"/> <br>
    <c:if test="${sessionScope.language == 'en'}">
        <input type="submit" value="log in"/>
    </c:if>
    <c:if test="${sessionScope.language == 'ru'}">
        <input type="submit" value="войти"/>
    </c:if>
</form>
<hr>
    <c:if test="${sessionScope.loginError == true}">
        <fmt:message key="Login.error"/>:<fmt:message key="either.pass.or.login.are.wrong"/>
    </c:if>
</c:if>
<c:if test="${sessionScope.regError == true}">
    <fmt:message key="Registration.error"/><br><fmt:message key="user.with.such.login.exists!"/>
</c:if>
<c:if test="${sessionScope.wrongData == true}">
    <fmt:message key="Registration.error"/>:<fmt:message key="Login.format"/>: 4-16 <fmt:message key="latin.symbols"/><br>
    <fmt:message key="Password.format"/>: 4-16 <fmt:message key="latin.symbols.or.special.symbols"/>
</c:if>

<c:if test="${sessionScope.userIsBlocked == true}">
    <fmt:message key="You.are.blocked.by.admin"/>!
</c:if>
<form action="ActivityTracker?command=setLanguage&lang=en" method="post">
    <input type="submit" value="english">
</form>

<form action="ActivityTracker?command=setLanguage&lang=ru" method="post">
    <input type="submit" value="русский">
</form>
<c:if test="${not empty sessionScope.regedAs}">
    <%
        if(session.getAttribute("regedAs").equals("user")) {
            response.sendRedirect("User.jsp");
        } else{
            response.sendRedirect("Admin.jsp");
        }
    %>
</c:if>
</body>
</html>