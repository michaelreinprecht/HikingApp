<%@ page import="myHikeJava.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="models.Month" %>
<%@ page import="models.Recommended" %>
<%@ page import="java.util.List" %><%--
=======
<%@ page import="models.Recommended" %><%--
>>>>>>> 351a70748077c064a381a650655fb1accf1bbd59
  Created by IntelliJ IDEA.
  User: cindy
  Date: 31.10.2023
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Detail</title>
    <!-- Bootstrap link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Font Awesome Icons link -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
    <!-- Link to detail.css -->
    <link rel="stylesheet" href="css/detail.css">
</head>
<body>
<nav class="navbar sticky-top navbar-expand-lg navbar-dark" style="background-color: #07773a; height: 80px">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02"
            aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item active">
                <a class="nav-link" href="index.jsp">Discover <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="create.jsp">Create Hike</a>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="#">Disabled</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container-fluid" style="background-color: white; padding: 0">
    <div class="row" style="width: 100%; margin: 0; padding: 0">
        <div class="col-md-4" style="margin-left: 10px;">
            <%
                String id = request.getParameter("Id");
                Hike hike = Database.getHikeById(Integer.parseInt(id));
            %><br>
            <h3 style="text-align: center; color: green; font-style: italic"><%=hike.getHikeName()%></h3><br>
            <label><b>Description:</b></label><br>
            <label><%=hike.getHikeDescription()%>
            </label><br>
            <label><b>Region:</b> <%=hike.getHikeRegion().getRegionName()%>
            </label><br>
            <label><b>Start Location:</b> Lon: <%=hike.getHikeStartLon()%> Lat: </label>
            <label><%=hike.getHikeStartLat()%>
            </label><br>
            <label><b>End Location:</b> Lon: <%=hike.getHikeEndLon()%> Lat: </label> <label><%=hike.getHikeEndLat()%>
        </label><br>
            <label><b>Altitude:</b> <%=hike.getHikeAltitude()%>m </label><br>
            <label><b>Distance:</b> <%=hike.getHikeDistance()%>km</label><br>
            <label><b>Duration:</b> <%=hike.getHikeDuration()%>
            </label><br>
            <label><b>Recommended Months:</b>
                <%
                List<Recommended> recommended = hike.getRecommendedList();
                int i;
                for (i=0; i < recommended.size() - 1;i++)  {
                %>
                <%= recommended.get(i).getMonth().getMonthName()%>,
                <%} %>
                <%= recommended.get(i).getMonth().getMonthName()%>
            </label> <br>

            <!-- Star ratings -->
            <label style="padding-right: 10px"><b>Landscape:</b></label>
            <%
                int landscapeRating = hike.getHikeLandscape();
                for (i = 0; i < 5; i++) {
                    if (i < landscapeRating) {
            %>
            <div class="star-rating">
                <i class="fas fa-star d-inline-block"></i>
            </div>
            <%
                    } else {
            %>
            <div class="inactive">
                <i class="fas fa-star d-inline-block"></i>
            </div>
            <%
                    }
                }
            %><br>

            <label style="padding-right: 10px"><b>Strength:</b></label>
            <%
                int strengthRating = hike.getHikeStrength();
                for (i = 0; i < 5; i++) {
                    if (i < strengthRating) {
            %>
            <div class="star-rating">
                <i class="fas fa-star d-inline-block"></i>
            </div>
            <%
            } else {
            %>
            <div class="inactive">
                <i class="fas fa-star d-inline-block"></i>
            </div>
            <%
                    }
                }
            %><br>

            <label style="padding-right: 10px"><b>Stamina:</b></label>
            <%
                int staminaRating = hike.getHikeStamina();
                for (i = 0; i < 5; i++) {
                    if (i < staminaRating) {
            %>
            <div class="star-rating">
                <i class="fas fa-star d-inline-block"></i>
            </div>
            <%
            } else {
            %>
            <div class="inactive">
                <i class="fas fa-star d-inline-block"></i>
            </div>
            <%
                    }
                }
            %><br>

            <label style="padding-right: 10px"><b>Overall Difficulty:</b></label>
            <%
                int difficultyRating = hike.getHikeDifficulty();
                for (i = 0; i < 5; i++) {
                    if (i < difficultyRating) {
            %>
            <div class="star-rating">
                <i class="fas fa-star d-inline-block"></i>
            </div>
            <%
            } else {
            %>
            <div class="inactive">
                <i class="fas fa-star d-inline-block"></i>
            </div>
            <%
                    }
                }
            %><br>

        </div>
        <div class="col-md" style="background-color: lightblue; padding: 0;">
            <img src="images/map.png" style="width: 100%; position: fixed" alt="Map Placeholder"/>
        </div>
    </div>
</div>
</body>
</html>
