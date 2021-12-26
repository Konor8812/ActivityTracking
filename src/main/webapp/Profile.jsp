<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="time" uri="/WEB-INF/customTag.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${empty sessionScope.language ? 'en' : sessionScope.language}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="loc"/>
<html>
<head>
    <link rel="stylesheet" href="style.css"/>
    <title><fmt:message key="Profile"/></title>
</head>
<body>
<form id="dateTime">
    <output>
        <time:dateTag/><br>
    </output>
</form>

<c:if test="${not empty sessionScope.regedAs}">
    <c:out value="${sessionScope.regedAs.login}"/><br>
    <fmt:message key="You.are"/> <fmt:message key="${sessionScope.regedAs.role}"/><br>
    <c:if test="${sessionScope.regedAs.role == 'admin'}"><br>
        <br>
        <img src="http://dl3.joxi.net/drive/2021/11/28/0038/3938/2494306/06/5150b13566.png" size="30%">
    </c:if>
    <c:if test="${sessionScope.regedAs.role == 'user'}">
        <fmt:message key="Your.points"/> ${sessionScope.regedAs.totalPoints}<br>
        <fmt:message key="You.have.taken"/> ${sessionScope.regedAs.activitiesAmount} <fmt:message key="activities"/><br>
        <fmt:message key="Forgot.password"/> <a href="ActivityTracker?command=changePass"><fmt:message key="Change.it!"/></a>
        <c:if test="${!empty sessionScope.showChangePassField}">
            <form action="ActivityTracker?command=changePass" method="post">
                <fmt:message key="Enter.new.pass"/> <br>
                <input type="text" name="newPassword">
                <c:if test="${sessionScope.language == 'en'}">
                <input type="submit" value="submit changes">
                </c:if>
                <c:if test="${sessionScope.language == 'ru'}">
                    <input type="submit" value="сменить">
                </c:if>
            </form>
        </c:if>
        <c:if test="${sessionScope.language == 'en'}">
        <c:if test="${!sessionScope.shouldShowUsersActivities}">
            <form action="ActivityTracker?command=showUsersActivities" method="post">
                <input type="submit" value="show your activities">
            </form>
        </c:if>
        <c:if test="${sessionScope.shouldShowUsersActivities}">
            <form action="ActivityTracker?command=hideUsersActivities" method="post">
                <input type="submit" value="hide your activities">
            </form>
        </c:if>
        </c:if>
        <c:if test="${sessionScope.language == 'ru'}">
        <c:if test="${!sessionScope.shouldShowUsersActivities}">
            <form action="ActivityTracker?command=showUsersActivities" method="post">
                <input type="submit" value="посмотреть свои активности">
            </form>
        </c:if>
        <c:if test="${sessionScope.shouldShowUsersActivities}">
            <form action="ActivityTracker?command=hideUsersActivities" method="post">
                <input type="submit" value="спрятать свои активности">
            </form>
        </c:if>
        </c:if>
        <c:if test="${sessionScope.shouldShowUsersActivities}">
            <table>
                <tr>
                    <th> <fmt:message key="Name"/></th>
                    <th> <fmt:message key="Duration"/></th>
                    <th> <fmt:message key="Reward"/></th>
                    <th> <fmt:message key="Description"/></th>
                    <th> <fmt:message key="Status"/></th>
                    <th> <fmt:message key="Time.spent"/></th>
                </tr>
                <c:forEach var="activity" items="${sessionScope.usersActivities}">
                    <tr>
                        <td>${activity.name}</td>
                        <td>${activity.duration}</td>
                        <td>${activity.reward}</td>
                        <td>${activity.description}</td>
                        <td><fmt:message key="${activity.status}"/></td>
                        <td>${activity.timeSpent}</td>
                        <c:if test="${activity.status == 'in_process'}">
                            <td>
                                <a href="ActivityTracker?command=deleteUsersActivity&activityId=${activity.id}&wasCompleted=true"><fmt:message key="completed"/></a>
                            </td>
                            <td>
                                <a href="ActivityTracker?command=deleteUsersActivity&activityId=${activity.id}&wasCompleted=false"><fmt:message key="give.up"/></a></td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </c:if>
    <c:if test="${sessionScope.language == 'en'}">
    <form method="post" action="ActivityTracker?command=returnToMain">
        <input type="submit" value="go back">
    </form>
    </c:if>
    <c:if test="${sessionScope.language == 'ru'}">
        <form method="post" action="ActivityTracker?command=returnToMain">
            <input type="submit" value="назад">
        </form>
    </c:if>
</c:if>

<script type="text/javascript" src="js/refreshTime.js">
    refreshTime()
</script>
</body>
</html>
