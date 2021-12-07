<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${empty sessionScope.language ? 'en' : sessionScope.language}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="loc"/>
<html>
<head>
    <link rel="stylesheet" href="style.css"/>
    <style>
        div {text-align: center}
    </style>
    <title><fmt:message key="Search.by.tag"/></title>
</head>
<body>
    <br>
    <div>
        <fmt:message key="Search.by.tag"/>!<br>
        <form method="post" action="ActivityTracker?command=searchByTag">
            <input type="text" name="tagName">
            <c:if test="${sessionScope.language == 'en'}">
            <input type="submit" value="Search!">
            </c:if>
            <c:if test="${sessionScope.language == 'ru'}">
                <input type="submit" value="Поиск!">
            </c:if>
        </form>
        <c:if test="${not empty sessionScope.activitiesByTag}">
            <table>
                <tr>
                    <th>  <fmt:message key="Name"/> </th>
                    <th>  <fmt:message key="Duration"/> </th>
                    <th>  <fmt:message key="Reward"/> </th>
                    <th>  <fmt:message key="Taken.by"/> </th>
                    <th> <fmt:message key="Description"/> </th>
                </tr>
                <c:forEach var="activity" items="${sessionScope.activitiesByTag}">
                    <tr>
                        <td>${activity.name}</td>
                        <td>${activity.duration}</td>
                        <td>${activity.reward}</td>
                        <td>${activity.takenByAmount}</td>
                        <td>${activity.description}</td>

                        <td><a href="ActivityTracker?command=deleteActivity&activityId=${activity.id}&goto=byTagSearch"> <fmt:message key="delete!"/> </a> </td>
                    </tr>
                </c:forEach>
            </table>

        </c:if>
        <c:if test="${sessionScope.invalidTagName}">
            <fmt:message key="unknown.tag"/>
        </c:if>
        <hr>
        <c:if test="${sessionScope.language == 'ru'}">
        <form action="ActivityTracker?command=showActivities&showDescription=false&numberOfSeries=0" method="post">
            <input type="submit" value="перейти к активностям!"/>
        </form>
        </c:if>
        <c:if test="${sessionScope.language == 'en'}">
        <form action="ActivityTracker?command=showActivities&showDescription=false&numberOfSeries=0" method="post">
            <input type="submit" value="go to activities!"/>
        </form>
        </c:if>
    </div>
    <c:if test="${sessionScope.regedAs.role != 'admin'}">
        <%
            response.sendRedirect("index.jsp");
        %>
    </c:if>
</body>
</html>
