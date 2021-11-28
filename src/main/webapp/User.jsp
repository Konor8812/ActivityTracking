<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${empty sessionScope.language ? 'en' : sessionScope.language}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="loc"/>
<html>
<head>
    <link rel="stylesheet" href="style.css"/>
    <title>Activity Tracker</title>
</head>
<body>
<c:if test="${sessionScope.regedAs.role == 'user'}">
    <hr>
    Success!
    <c:out value="You are logged as ${sessionScope.regedAs.login}" />
    <form action="ActivityTracker?command=logOut" method="post">
        <input type="submit" value="log out!"/>
    </form>
<c:if test="${sessionScope.shouldShowActivities == true}">

    <table>
    <tr>
    <th> Name </th>
    <th> Duration </th>
    <th> Reward </th>
    <th>
    <c:if test="${!sessionScope.shouldShowTags}">
        <a href="ActivityTracker?command=getActivitiesDescription">Show tags</a>
    </c:if>
    <c:if test="${sessionScope.shouldShowTags}">
        <a href="ActivityTracker?command=hideActivitiesDescription">Hide tags</a>
    </c:if>
    </th>
        <th></th>
    </tr>
    <c:forEach var="activity" items="${sessionScope.activities}">
        <tr>
            <td>${activity.name}</td>
            <td>${activity.duration}</td>
            <td>${activity.reward}</td>
            <td>
                <c:if test="${sessionScope.shouldShowTags}">
                    <c:out value="${activity.description}" />
                </c:if>
            </td>
            <td><a href="ActivityTracker?command=reqActivity&userId=${sessionScope.regedAs.id}&activityId=${activity.id}">take activity</a></td>
        </tr>
    </c:forEach>
    </table>
    <c:if test="${sessionScope.activityTaken}">
        <c:out value="You have already taken this activity!"/>
    </c:if>
</c:if>
<form method="post" action="ActivityTracker?command=showProfile">
    <input type="submit" value="profile">
</form>
</c:if>
    <c:if test="${sessionScope.regedAs.role ne 'user'}">
        <c:out value="Access denied!!!"/><br>
        <a href="index.jsp">Log in</a>
    </c:if>
</body>
</html>
