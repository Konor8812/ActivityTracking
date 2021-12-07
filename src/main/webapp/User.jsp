<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
<c:if test="${sessionScope.regedAs.role == 'user'}">
    <hr>
    <fmt:message key="Success!"/>
    <fmt:message key="You.are.logged.as"/> ${sessionScope.regedAs.login}"<hr>
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
<c:if test="${sessionScope.shouldShowActivities == true}">

    <table>
    <tr>
    <th> <fmt:message key="Name"/> </th>
    <th> <fmt:message key="Duration"/> </th>
    <th> <fmt:message key="Reward"/> </th>
    <th>
    <c:if test="${!sessionScope.shouldShowTags}">
        <a href="ActivityTracker?command=getActivitiesDescription"><fmt:message key="Show.tags"/></a>
    </c:if>
    <c:if test="${sessionScope.shouldShowTags}">
        <a href="ActivityTracker?command=hideActivitiesDescription"><fmt:message key="Hide.tags"/></a>
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
            <td><a href="ActivityTracker?command=reqActivity&userId=${sessionScope.regedAs.id}&activityId=${activity.id}"><fmt:message key="take.activity"/></a></td>
        </tr>
    </c:forEach>
    </table>
    <c:if test="${sessionScope.numberOfSeries + 5 < sessionScope.totalActivitiesAmount}">
        <a href="ActivityTracker?command=showActivities&numberOfSeries=${sessionScope.numberOfSeries+5}"><fmt:message key="next"/> 5 </a>
    </c:if><br>
    <c:if test="${sessionScope.numberOfSeries > 0}">

    <a href="ActivityTracker?command=showActivities&numberOfSeries=${sessionScope.numberOfSeries-5}"><fmt:message key="prev"/> 5 </a>
</c:if><br>
    <a href="ActivityTracker?command=showActivities&num=0"><fmt:message key="to.begin"/></a><hr>
    <c:if test="${sessionScope.activityTaken}">
        <fmt:message key="You.have.already.taken.this.activity"/>
    </c:if>
</c:if>

</c:if>
    <c:if test="${sessionScope.regedAs.role ne 'user'}">
        <fmt:message key="Access.denied!!!"/>
        <a href="index.jsp"><fmt:message key="Log.in"/></a>
    </c:if>
<c:if test="${empty sessionScope.regedAs}">
    <fmt:message key="Access.denied!!!"/><br>
    <a href="index.jsp"><fmt:message key="Log.in"/></a>
</c:if>
</body>
</html>
