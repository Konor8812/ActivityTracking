<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="style.css"/>
    <title>Users requests</title>
</head>
<body>
<br>
User ${sessionScope.user.id}, login ==> ${sessionScope.user.login}<hr><br>
has ${sessionScope.user.requestsAmount} requests and ${sessionScope.user.activitiesAmount} taken activities<br>
<table>
    <tr>
        <th> Name </th>
        <th> Duration </th>
        <th> Reward </th>
        <th> Description </th>
    </tr>
    <c:forEach var="activity" items="${sessionScope.requests}">
        <tr>
            <td>${activity.name}</td>
            <td>${activity.duration}</td>
            <td>${activity.reward}</td>
            <td>${activity.description}</td>
            <td><a href="ActivityTracker?command=regActivityForUser&activityId=${activity.id}&userId=${sessionScope.user.id}"> approve! </a> </td>
            <td><a href="ActivityTracker?command=denyApproval&activityId=${activity.id}&userId=${sessionScope.user.id}"> deny! </a> </td>
        </tr>
    </c:forEach>
    <hr>
    <form action="ActivityTracker?command=showUsers" method="post">
        <input type="submit" value="back to all users"/>
    </form>
</table>
</body>
</html>
