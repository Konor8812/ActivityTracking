<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="style.css"/>
    <title>ActivityTracker</title>
</head>
<body>
    Loged in as admin
    <form action="ActivityTracker?command=deleteAllUsers" method="post">
        <input type="submit" value="delete all users!"/>
    </form>
    <c:if test="${sessionScope.shouldPrintUsers == true}">
        <table>
            <tr>
                <th> Login</th>
                <th> Activities amount</th>
                <th> has requests</th>
            </tr>

            <c:forEach var="user" items="${sessionScope.users}">
                <tr>
                    <td>${user.login}</td>
                    <td></td>
                    <td></td>
                </tr>
            </c:forEach>

        </table>
    </c:if>
</body>
</html>
