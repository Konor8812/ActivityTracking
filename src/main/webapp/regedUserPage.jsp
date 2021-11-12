<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Activity Tracker</title>
</head>
<body>
    <hr>
    Success!
    <c:out value="You are logged as ${sessionScope.login}" />

</body>
</html>
