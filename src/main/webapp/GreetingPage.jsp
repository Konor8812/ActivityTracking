<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-16BE" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${empty sessionScope.language ? 'en' : sessionScope.language}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="loc"/>
<html>
<head>
    <style>
        body {
            background-color: bisque;
        }
        .c2 {
            text-align: center;
            background-color: #47ffe9;
            position: absolute;
            top: 180px;
            left: 600px;
        }

        .forLogInButton {
            position: absolute;
            top: 40px;
            left: 1440px;
        }

        .forRegButton {
            position: absolute;
            top: 10px;
            left: 1410px;
        }

        .forEngButton {
            position: absolute;
            top: 10px;
            left: 10px;
        }

        .forRuButton {
            position: absolute;
            top: 40px;
            left: 10px;
        }
    </style>
    <title><fmt:message key="Greetings"/></title>
</head>
<body>
<form action="ActivityTracker?command=setLanguage&lang=en" method="post">
    <input type="submit" value="English" class="forEngButton">
</form>
<form action="ActivityTracker?command=setLanguage&lang=ru" method="post">
    <input type="submit" value="Русский" class="forRuButton">
</form>
<c:if test="${sessionScope.language == 'en'}">
<a href="Registration.jsp" class="forRegButton">
    <input type="button" value="Registration"/>
</a>
<a href="LogIn.jsp" class="forLogInButton">
    <input type="button" value="Log In"/>
</a>
</c:if>
<c:if test="${sessionScope.language == 'ru'}">

    <a href="Registration.jsp" class="forRegButton">
        <input type="button" value="Регистрация"/>
    </a>
    <a href="LogIn.jsp" class="forLogInButton">
        <input type="button" value="Войти"/>
    </a>
</c:if>
<div class="c2">
    <fmt:message key="Greeting"/>
</div>
</body>
</html>
