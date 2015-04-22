<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/chart.min.js/chart.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/crap.js"></script>
    <script type="text/javascript">
        $(function() {
            options = {
                //Boolean - Show a backdrop to the scale label
                scaleShowLabelBackdrop: true,
                //String - The colour of the label backdrop
                scaleBackdropColor: "rgba(255,255,255,0.75)",
                // Boolean - Whether the scale should begin at zero
                scaleBeginAtZero: true,
                //Number - The backdrop padding above & below the label in pixels
                scaleBackdropPaddingY: 2,
                //Number - The backdrop padding to the side of the label in pixels
                scaleBackdropPaddingX: 2,
                //Boolean - Show line for each value in the scale
                scaleShowLine: true,
                //Boolean - Stroke a line around each segment in the chart
                segmentShowStroke: true,
                //String - The colour of the stroke on each segement.
                segmentStrokeColor: "#fff",
                //Number - The width of the stroke value in pixels
                segmentStrokeWidth: 2,
                //Number - Amount of animation steps
                animationSteps: 100,
                //String - Animation easing effect.
                animationEasing: "easeOutBounce",
                //Boolean - Whether to animate the rotation of the chart
                animateRotate: true,
                //Boolean - Whether to animate scaling the chart from the centre
                animateScale: false


            };
            data = [

                <c:set var="counter" value="0" />
                <c:forEach var="color" items="${colors}" varStatus="status">
                    <c:if test="${counter le 10}">
                    {
                        value: ${orderedList[status.index].value},
                        color:"${color.key}",
                        highlight: "${color.value}",
                        label: "${orderedList[status.index].key}"
                    },
                    <c:set var="counter" value="${counter + 1}" />
                    </c:if>
                </c:forEach>
            ];
            ctx = $("#myChart").get(0).getContext("2d");
            myNewChart = new Chart(ctx).Pie(data, options);
        });

    </script>
</head>
<body>

<canvas id="myChart" width="400" height="400"></canvas>

<div style="clear:left;">
    <c:forEach var="color" items="${colors}" varStatus="status">
        <c:if test="${status.count le 10}">
            <span style="color: ${color.key};">${orderedList[status.index].key}</span><br />
        </c:if>
    </c:forEach>
</div>

</body>
</html>
