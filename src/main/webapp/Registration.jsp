<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-16BE" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${empty sessionScope.language ? 'en' : sessionScope.language}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="loc"/>
<html>
<head>
    <title>
        <fmt:message key="ActivityTracker"/>
    </title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" >
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="indexStyle.css">
</head>
<body>
<div class="wrapper fadeInDown">
    <div id="formContent">

        <div class="fadeIn first">
            <img src="http://dl4.joxi.net/drive/2021/11/26/0038/3938/2494306/06/cac8baea54.png" id="icon" alt="User Icon" size="30%" />
        </div>

        <form action="ActivityTracker?command=regUser" method="post">
            <input type="text" class="fadeIn second" name="login" placeholder="login">
            <input type="text" class="fadeIn third" name="password" placeholder="password">
            <c:if test="${sessionScope.language == 'en'}">
            <input type="submit" class="fadeIn fourth" value="Register">
            <a href="GreetingPage.jsp" >
                <input type="button" value="Back" class="fadeIn fourth"/>
            </a>
            </c:if>
            <c:if test="${sessionScope.language == 'ru'}">
                <input type="submit" class="fadeIn fourth" value="Зарегистрировать">
                <a href="GreetingPage.jsp" >
                    <input type="button" value="Назад" class="fadeIn fourth"/>
                </a>
            </c:if>
        </form>
        <c:if test="${sessionScope.wrongData == true}">
            <fmt:message key="Registration.error"/>:<fmt:message key="Login.format"/>: 4-16 <fmt:message key="latin.symbols"/><br>
            <fmt:message key="Password.format"/>: 4-16 <fmt:message key="latin.symbols.or.special.symbols"/>
        </c:if>
        <c:if test="${sessionScope.regError == true}">
            <fmt:message key="Registration.error"/><br><fmt:message key="user.with.such.login.exists!"/>
        </c:if>

    </div>
</div>
</body>
</html>