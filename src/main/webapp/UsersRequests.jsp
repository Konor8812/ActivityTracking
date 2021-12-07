<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${empty sessionScope.language ? 'en' : sessionScope.language}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="loc"/>
<html>
<head>
    <link rel="stylesheet" href="style.css"/>
    <title><fmt:message key="Users.requests"/></title>
</head>
<body>
<br>
<fmt:message key="User.id"/> ${sessionScope.user.id}, <fmt:message key="login"/> ==> ${sessionScope.user.login}<hr><br>
    <fmt:message key="has"/> ${sessionScope.user.requestsAmount} <fmt:message key="requests"/> <fmt:message key="and"/> ${sessionScope.user.activitiesAmount} <fmt:message key="taken.activities"/><br>
<table>
    <tr>
        <th> <fmt:message key="Name"/> </th>
        <th> <fmt:message key="Duration"/> </th>
        <th> <fmt:message key="Reward"/> </th>
        <th> <fmt:message key="Description"/> </th>
    </tr>
    <c:forEach var="activity" items="${sessionScope.requests}">
        <tr>
            <td>${activity.name}</td>
            <td>${activity.duration}</td>
            <td>${activity.reward}</td>
            <td>${activity.description}</td>
            <td><a href="ActivityTracker?command=regActivityForUser&activityId=${activity.id}&userId=${sessionScope.user.id}"> <fmt:message key="approve"/> </a> </td>
            <td><a href="ActivityTracker?command=denyApproval&activityId=${activity.id}&userId=${sessionScope.user.id}"> <fmt:message key="deny"/> </a> </td>
        </tr>
    </c:forEach>
    <hr>
    <c:if test="${sessionScope.language == 'en'}">
        <form action="ActivityTracker?command=showUsers" method="post">
            <input type="submit" value="back to all users"/>
        </form>
    </c:if>
    <c:if test="${sessionScope.language == 'ru'}">
    <form action="ActivityTracker?command=showUsers" method="post">
        <input type="submit" value="назад к списку всех пользователей"/>
    </form>
    </c:if>
</table>
<c:if test="${sessionScope.regedAs != 'admin'}">
    <%
        response.sendRedirect("index.jsp");
    %>
</c:if>
</body>
</html>