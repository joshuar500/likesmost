<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>
<html>
<head>
    <title></title>
</head>
<body>
This thingz<br />
<a href="${sessionScope.authorizationUrl}">Click here to authorize</a>
<br />

</body>
</html>
