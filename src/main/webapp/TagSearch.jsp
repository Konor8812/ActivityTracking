<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${empty sessionScope.language ? 'en' : sessionScope.language}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="loc"/>
<html>
<head>
    <style>
        div {text-align: center}
    </style>
    <title><fmt:message key="Search.by.tag"/></title>
</head>
<body>
    <br>
    <div>
        <form method="post" action="ActivityTracker?command=getActivitiesWithTag">
            <input type="text" name="tagName">
            <c:if test="${sessionScope.languale == 'en'}">
            <input type="submit" value="Search!">
            </c:if>
            <c:if test="${sessionScope.languale == 'ru'}">
                <input type="submit" value="Поиск!">
            </c:if>
        </form>

    </div>
</body>
</html>
