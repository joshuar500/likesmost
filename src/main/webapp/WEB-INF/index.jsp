<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/crap.js"></script>
    <script type="text/javascript" src="<c:url value="/static/chart.min.js/chart.min.js" />"></script>
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
                {
                    value: 300,
                    color: "#F7464A",
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
            ];
            ctx = $("#myChart").get(0).getContext("2d");
            myNewChart = new Chart(ctx).Pie(data, options);
        });

    </script>
</head>
<body>
This thing<br />
<a href="${sessionScope.authorizationUrl}">Click here to authorize</a>
<br />
<canvas id="myChart" width="400" height="400"></canvas>

</body>
</html>
