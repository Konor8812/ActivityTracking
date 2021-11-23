<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="style.css"/>
    <title>ActivityTracker</title>
</head>
<body>
<c:if test="${sessionScope.regedAs.role == 'admin'}">
    Logged in as admin
    <form action="ActivityTracker?command=logOut" method="post">
        <input type="submit" value="logOut" name="log out"/>
    </form>
    <form method="post" action="ActivityTracker?command=showProfile">
        <input type="submit" value="profile">
    </form>
    <c:if test="${sessionScope.shouldShowUsers == true}">
        <form action="ActivityTracker?command=showActivities&showDescription=false" method="post">
            <input type="submit" value="get activities!"/>
        </form><hr>
        <form action="ActivityTracker?command=showOnlyBlockedUsers" method="post">
            <input type="submit" value="get blocked users only">
        </form>
        <table>
            <tr>
                <th> Login </th>
                <th> Status </th>
                <th> Activities amount</th>
                <th> total points </th>
                <th> Number of requested activities </th>
                <th> Block user </th>
            </tr>

            <c:forEach var="user" items="${sessionScope.users}">
                <tr>
                    <td>${user.login}</td>
                    <td>${user.status}</td>
                    <td>${user.activitiesAmount}</td>
                    <td>${user.totalPoints}</td>
                    <td>
                        <c:if test="${user.requestsAmount != 0}">
                            <a href="ActivityTracker?command=getUsersRequests&userId=${user.id}">${user.requestsAmount}</a>
                        </c:if>
                        <c:if test="${user.requestsAmount == 0}">
                            ${user.requestsAmount}
                        </c:if>
                    </td>
                    <td> <a href="ActivityTracker?command=blockUser&userId=${user.id}">block!</a> </td>
                    <td> <a href="ActivityTracker?command=deleteUser&userId=${user.id}">delete!</a> </td>
                </tr>
            </c:forEach>
            <form action="ActivityTracker?command=deleteAllUsers" method="post">
                <input type="submit" value="delete all users!"/>
            </form>

        </table>
    </c:if>
    <c:if test="${sessionScope.shouldShowBlockedUsers == true}">
        <form action="ActivityTracker?command=showUsers" method="post">
            <input type="submit" value="back to all users"/>
        </form>
        <table>
            <tr>
                <th> Login </th>
                <th> Activities amount</th>
                <th> total points </th>
                <th> Unblock user </th>
            </tr>
            <c:forEach var="user" items="${sessionScope.blockedUsers}">
                <tr>
                    <td>${user.login}</td>
                    <td>${user.activitiesAmount}</td>
                    <td>${user.totalPoints}</td>
                    <td> <a href="ActivityTracker?command=unblockUser&userId=${user.id}">unblock!</a> </td>
                    <td> <a href="ActivityTracker?command=deleteUser&userId=${user.id}">delete!</a> </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${sessionScope.shouldShowActivities == true}">
        <form action="ActivityTracker?command=showUsers" method="post">
            <input type="submit" value="get users!"/>
        </form>
        <table>
            <tr>
                <th> <a href="ActivityTracker?command=sortActivities&sortBy=name"> Name </a></th>
                <th> <a href="ActivityTracker?command=sortActivities&sortBy=duration"> Duration </a></th>
                <th> <a href="ActivityTracker?command=sortActivities&sortBy=reward"> Reward </a></th>
                <th> <a href="ActivityTracker?command=sortActivities&sortBy=numberOfTakes"> Taken by </a></th>
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
                    <td>${activity.takenByAmount}</td>
                    <td>
                    <c:if test="${sessionScope.shouldShowTags}">
                        <c:out value="${activity.description}" />
                    </c:if>
                    </td>
                    <td><a href="ActivityTracker?command=deleteActivity&activityId=${activity.id}"> delete </a> </td>
                </tr>
            </c:forEach>
        </table>
        <b>Add new activity</b>
        <form method="post" action="ActivityTracker?command=addActivity">
            <i>Name - </i>
            <input type="text" name="name" ><br>
            <i>Duration (format - </i> <em>X hours/days</em> <i>) - </i>
            <input type="text" name="duration"> <br>
            <i>Reward - </i>
            <input type="text" name="reward"><br>
            <i>Description - </i>
            <input type="text" name="description"><br>
            <input type="submit">
        </form>
        <c:if test="${sessionScope.wrongDurationFormat}">
            Wrong duration Format!
        </c:if>
        <c:if test="${sessionScope.activityExists}">
            Activity with such name already exists!
        </c:if>
    </c:if>
</c:if>
<c:if test="${sessionScope.regedAs.role ne 'admin'}">
    <c:out value="Access denied!!!"/><br>
    <a href="index.jsp">Log in</a>
</c:if>
</body>
</html>
