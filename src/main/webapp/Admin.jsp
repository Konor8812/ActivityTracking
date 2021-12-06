<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${empty sessionScope.language ? 'en' : sessionScope.language}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="loc"/>
<html>
<head>
    <link rel="stylesheet" href="style.css"/>
    <title><fmt:message key="ActivityTracker"/></title>
</head>
<body>
<c:if test="${sessionScope.regedAs.role == 'admin'}">
    <fmt:message key="Logged.in.as"/> <fmt:message key="admin"/>
    <c:if test="${sessionScope.language == 'en'}">
    <form action="ActivityTracker?command=logOut" method="post">
        <input type="submit" value="logOut" name="log out"/>
    </form>
    <form method="post" action="ActivityTracker?command=showProfile">
        <input type="submit" value="profile">
    </form>
    </c:if>
    <c:if test="${sessionScope.language == 'ru'}">
        <form action="ActivityTracker?command=logOut" method="post">
            <input type="submit" value="выйти" />
        </form>
        <form method="post" action="ActivityTracker?command=showProfile">
            <input type="submit" value="профиль">
        </form>
    </c:if>

    <c:if test="${sessionScope.shouldShowUsers == true}">
        <c:if test="${sessionScope.language == 'en'}">
        <form action="ActivityTracker?command=showActivities&showDescription=false&numberOfSeries=0" method="post">
            <input type="submit" value="go to activities!"/>
        </form><hr>
        <form action="ActivityTracker?command=showOnlyBlockedUsers" method="post">
            <input type="submit" value="get blocked users only">
        </form>
        <form action="ActivityTracker?command=deleteAllUsers" method="post">
            <input type="submit" value="delete all users!"/>
        </form>
        </c:if>
        <c:if test="${sessionScope.language == 'ru'}">
            <form action="ActivityTracker?command=showActivities&showDescription=false&numberOfSeries=0" method="post">
                <input type="submit" value="перейти к активностям!"/>
            </form><hr>
            <form action="ActivityTracker?command=showOnlyBlockedUsers" method="post">
                <input type="submit" value="показать только заблокированых пользователей">
            </form>
            <form action="ActivityTracker?command=deleteAllUsers" method="post">
                <input type="submit" value="удалить всех пользователей!"/>
            </form>
        </c:if>
        <table>
            <tr>
                <th> <fmt:message key="Login"/> </th>
                <th> <fmt:message key="Status"/> </th>
                <th> <fmt:message key="Activities.amount"/></th>
                <th> <fmt:message key="Total.points"/> </th>
                <th> <fmt:message key="Number.of.requested.activities"/> </th>
                <th> <fmt:message key="Block.user"/> </th>
            </tr>

            <c:forEach var="user" items="${sessionScope.users}">
                <tr>
                    <td>${user.login}</td>
                    <td><fmt:message key="${user.status}"/></td>
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
                    <td> <a href="ActivityTracker?command=blockUser&userId=${user.id}"><fmt:message key="block!"/></a> </td>
                    <td> <a href="ActivityTracker?command=deleteUser&userId=${user.id}"><fmt:message key="delete!"/></a> </td>
                </tr>
            </c:forEach>

        </table>
    </c:if>
    <c:if test="${sessionScope.shouldShowBlockedUsers == true}">
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
        <table>
            <tr>
                <th> <fmt:message key="Login"/> </th>
                <th> <fmt:message key="Activities.amount"/> </th>
                <th> <fmt:message key="Total.points"/> </th>
                <th> <fmt:message key="Unblock.user"/> </th>
            </tr>
            <c:forEach var="user" items="${sessionScope.blockedUsers}">
                <tr>
                    <td>${user.login}</td>
                    <td>${user.activitiesAmount}</td>
                    <td>${user.totalPoints}</td>
                    <td> <a href="ActivityTracker?command=unblockUser&userId=${user.id}"><fmt:message key="unblock!"/></a> </td>
                    <td> <a href="ActivityTracker?command=deleteUser&userId=${user.id}"><fmt:message key="delete!"/></a> </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${sessionScope.shouldShowActivities == true}">
        <c:if test="${sessionScope.language == 'en'}">
        <form action="ActivityTracker?command=showUsers" method="post">
            <input type="submit" value="get users!"/>
        </form>
            <form action="TagSearch.jsp" method="post">
                <input type="submit" value="search activity by tag"/>
            </form>
        </c:if>
        <c:if test="${sessionScope.language == 'ru'}">
            <form action="ActivityTracker?command=showUsers" method="post">
                <input type="submit" value="показать пользователей!"/>
            </form>
            <form action="TagSearch.jsp">
                <input type="submit" value="поиск по метке"/>
            </form>
        </c:if>
        <table>
            <tr>
                <th> <a href="ActivityTracker?command=sortActivities&sortBy=name"> <fmt:message key="Name"/> </a></th>
                <th> <a href="ActivityTracker?command=sortActivities&sortBy=duration"> <fmt:message key="Duration"/> </a></th>
                <th> <a href="ActivityTracker?command=sortActivities&sortBy=reward"> <fmt:message key="Reward"/> </a></th>
                <th> <a href="ActivityTracker?command=sortActivities&sortBy=numberOfTakes"> <fmt:message key="Taken.by"/> </a></th>
                <th>
                    <c:if test="${!sessionScope.shouldShowTags}">
                        <a href="ActivityTracker?command=getActivitiesDescription"><fmt:message key="Show.tags"/></a>
                    </c:if>
                    <c:if test="${sessionScope.shouldShowTags}">
                        <a href="ActivityTracker?command=hideActivitiesDescription"><fmt:message key="Hide.tags"/></a>
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
                    <td><a href="ActivityTracker?command=deleteActivity&activityId=${activity.id}"> <fmt:message key="delete!"/> </a> </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${sessionScope.numberOfSeries + 5< sessionScope.totalActivitiesAmount}">
            <a href="ActivityTracker?command=showActivities&numberOfSeries=${sessionScope.numberOfSeries+5}"><fmt:message key="next"/> 5 </a>
        </c:if><br><br>
            <a href="ActivityTracker?command=showActivities&num=0"><fmt:message key="to.begin"/></a>
<hr>
        <b><fmt:message key="Add.new.activity"/></b>
        <form method="post" action="ActivityTracker?command=addActivity">
            <i><fmt:message key="Name"/> - </i>
            <input type="text" name="name" ><br>
            <i><fmt:message key="Duration"/> </i>
            <input type="text" name="duration"> <br>
            <i><fmt:message key="Reward"/> - </i>
            <input type="text" name="reward"><br>
            <i><fmt:message key="Description"/> - </i>
            <input type="text" name="description"><br>
            <input type="submit">
        </form>
<hr>
        <form method="post" action="ActivityTracker?command=loadProperty">
            <i> <fmt:message key="Enter.translation"/> </i><br>
            <i><fmt:message key="Key"/> - </i>
            <input type="text" name="key" ><br>
            <i><fmt:message key="Value"/> - </i>
            <input type="text" name="value"> <br>

            <input type="submit">
        </form>
        <c:if test="${sessionScope.propertyExists}">
            <fmt:message key="property.exists"/>
        </c:if>
<c:if test="${sessionScope.wrongTranslation}">
    <fmt:message key="Wrong.translation.format"/>
    <fmt:message key="Should.be"/>:
    <fmt:message key="Translation.format"/>
</c:if>
        <c:if test="${sessionScope.wrongDataFormat}">
            <fmt:message key="Wrong.data.Format!"/><br>
            <fmt:message key="Should.be"/>:
            <fmt:message key="Name.for.activity.format"/> <br>
            <fmt:message key="Duration.for.activity.format"/><br>
            <fmt:message key="Reward.for.activity.format"/><br>
            <fmt:message key="Description.for.activity.format"/><br>
        </c:if>
        <c:if test="${sessionScope.activityExists}">
            <fmt:message key="Activity.with.such.name.already.exists!"/>
        </c:if>
    </c:if>
</c:if>
<c:if test="${sessionScope.regedAs.role ne 'admin'}">
    <fmt:message key="Access.denied!!!"/>
    <a href="index.jsp"><fmt:message key="Log.in"/></a>
</c:if>
<c:if test="${empty sessionScope.regedAs}">
    <fmt:message key="Access.denied!!!"/><br>
    <a href="index.jsp"><fmt:message key="Log.in"/></a>
</c:if>
</body>
</html>
