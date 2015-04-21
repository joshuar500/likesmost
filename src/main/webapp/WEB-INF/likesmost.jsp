<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script type="text/javascript" src="<c:url value="/chart.min.js/chart.min.js" />"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/crap.js"></script>
    <script type="text/javascript">
        var ctx = document.getElementById("myChart").getContext("2d");
        var myNewChart = new Chart(ctx[1]).Doughnut(data);

        var data = [
            {
                value: 300,
                color:"#F7464A",
                highlight: "#FF5A5E",
                label: "Red"
            },
            {
                value: 50,
                color: "#46BFBD",
                highlight: "#5AD3D1",
                label: "Green"
            },
            {
                value: 100,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "Yellow"
            }
        ]

    </script>
</head>
<body>

<canvas id="myChart" width="400" height="400"></canvas>

<c:forEach var="entry" items="${orderedList}">
    ${entry.key} : ${entry.value}<br />
</c:forEach>

</body>
</html>
