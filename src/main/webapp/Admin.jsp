<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="style.css"/>
    <title>ActivityTracker</title>
</head>
<body>
    Loged in as admin


    <c:if test="${sessionScope.shouldShowUsers == true}">
        <form action="ActivityTracker?command=showActivities&showDescription=false" method="post">
            <input type="submit" value="get activities!"/>
        </form>
        <table>
            <tr>
                <th> Login</th>
                <th> Activities amount</th>
                <th> has requests</th>
            </tr>

            <c:forEach var="user" items="${sessionScope.users}">
                <tr>
                    <td>${user.login}</td>
                    <td>${user.activitiesAmount}</td>
                    <td>${user.totalPoints}</td>
                    <td> <a href="ActivityTracker?command=deleteUser&login=${user.login}">delete!</a> </td>
                </tr>
            </c:forEach>
            <form action="ActivityTracker?command=deleteAllUsers" method="post">
                <input type="submit" value="delete all users!"/>
            </form>
        </table>
    </c:if>
    <c:if test="${sessionScope.shouldShowActivities == true}">
        <form action="ActivityTracker?command=showUsers" method="post">
            <input type="submit" value="get users!"/>
        </form>
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
                </tr>
            </c:forEach>
        </table>
    </c:if>

</body>
</html>
