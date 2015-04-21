<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>
<html>
<head>
    <title></title>
</head>
<body>

<c:forEach var="entry" items="${orderedList}">
    ${entry.key} : ${entry.value}<br />
</c:forEach>

</body>
</html>
