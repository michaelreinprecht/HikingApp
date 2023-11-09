<%@ page import="com.example.myhike.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="com.example.myhike.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="com.example.myhike.StarRatingGenerator" %>
<%@ page import="models.Recommended" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Month" %>
<%@ page import="models.Recommended" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hike Details</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Font Awesome Icons link -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

    <!-- Link to detail.css -->

    <link rel="stylesheet" href="detail.css">
    <style>
        body {
            margin-left: 20px;
        }
        .content {
            display: none;
            background-color: white;
            padding: 50px;
            margin-bottom: 20px;
        }
        .container {
            display: flex;
            justify-content: space-between;

        }
        .col-md-6 {
          /*  flex: 0 0 48%;*/ /* Anpassen der Breite für die Bilder */
            text-align: center;
        }
        .text-center {
            text-align: center;

        }
        .button-group {
            display: flex;
            flex-direction: column;
        }

        .row{
            text-align: center;
        }



    </style>
    <script>
        function toggleContent(buttonId) {
            var content = document.getElementById(buttonId + "-content");
            if (content.style.display === "none" || content.style.display === "") {
                content.style.display = "block";
            } else {
                content.style.display = "none";
            }
        }
    </script>
</head>
<body>
<!-- Navbar -->
<nav class="navbar sticky-top navbar-expand-lg navbar-dark" style="background-color: #07773a; height: 80px">
    <a class="navbar-brand" href="#">
        <img src="images/icon3.png" alt="MyHike" style=" width: 90px; height: 70px; margin-bottom: 5px">
    </a>
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
                <a class="nav-link" href="#">Routes</a>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="#">Disabled</a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>

    <%String id = request.getParameter("Id");
        Hike hike = Database.getHikeById(Integer.parseInt(id));
    %>

    <div class ="name" style="text-align: center" >

            <h3 class="text-center" style="color: green; margin-top: 40px; font-style: italic"><%= hike.getHikeName() %></h3>
    </div>
<!-- Hike Details -->
<div class="container" style="margin-left: 100px">

    <div class="row">
        <div class="col-md-6">

            <div style="display: flex; width: 100%; align-items: center; margin-left: 50px; margin-top: 20px">
                <img src="images/uhr_dauer.png" alt="uhr" style="width:40px; height: 40px; margin-top: 15px; margin-left: 150px; margin-right: 5px;">
                <h5 class="text-center" style="color: green; margin-top: 20px; font-style: italic"><%= hike.getHikeDuration()%> hours</h5>
                <img src="images/streckenlänge.png" alt="streckenlänge" style="width:50px; height: 40px; margin-top: 15px; margin-left: 100px; margin-right: 5px;">
                <h5 class="text-center" style="color: green; margin-top: 20px; font-style: italic"><%= hike.getHikeDistance()%>km</h5>
                <img src="images/region.png" alt="region" style="width:50px; height: 40px; margin-top: 20px; margin-left: 100px; margin-right: 5px">
                <h5 class="text-center" style="color: green; margin-top: 20px; font-style: italic"><%= hike.getHikeRegion().getRegionName()%></h5>
                <img src="images/months_icon.png" alt="monate" style="width:50px; height: 40px; margin-top: 20px; margin-left: 100px; margin-right: 5px;">
                <h5 class="text-center" style="color: green; margin-top: 20px; font-style: italic">
                    <%
                        List<Recommended> recommended = hike.getRecommendedList();
                        for (int i = 0; i < recommended.size(); i++) {
                    %>
                    <%= recommended.get(i).getMonth().getMonthName()%>
                    <% if (i < recommended.size() - 1) { %>  <% } %>
                    <%
                        }
                    %>
                </h5>
            </div>


            <div class="image-container" style="display: flex; align-items: center; margin-top: 20px; margin-bottom: 20px">
                <!-- Rundgangsbild -->
                <img src="images/beispiel_berge.jpg" alt="Rundgangsbild" style="width: 100%; height: 400px; margin-left: 20px; margin-right: 20px">

                <!-- Karte -->
                <img src="images/map.png" alt="Karte" style="width: 100%; height: 400px; margin-left: 20px">
            </div>
        </div>


            <!-- Buttons Container -->
            <div class="button-group" style="width: 220%; margin-left: 50px; display: flex; flex-direction: column; justify-content: space-between">
                <!-- Beschreibung -->
                <button class="btn btn-light" onclick="toggleContent('beschreibung')">Beschreibung</button>
                <div id="beschreibung-content" class="content">
                    <p>Hier ist die Beschreibung des Hikes.</p>
                </div>

                <!-- Streckeneigenschaften -->
                <button class="btn btn-light" onclick="toggleContent('streckeneigenschaften')">Streckeneigenschaften</button>
                <div id="streckeneigenschaften-content" class="content">
                    <div class="ratings-container">
                        <div class="rating-label"><b>Landscape:</b></div>
                        <%= StarRatingGenerator.generateStarRating(5, hike.getHikeLandscape()) %>

                        <div class="rating-label"><b>Strength:</b></div>
                        <%= StarRatingGenerator.generateStarRating(5, hike.getHikeStrength()) %>

                        <div class="rating-label"><b>Stamina:</b></div>
                        <%= StarRatingGenerator.generateStarRating(5, hike.getHikeStamina()) %>

                        <div class="rating-label"><b>Overall Difficulty:</b></div>
                        <%= StarRatingGenerator.generateStarRating(5, hike.getHikeDifficulty()) %>
                    </div>
                </div>

                <!-- Einkehrmöglichkeiten -->
                <button class="btn btn-light" onclick="toggleContent('einkehrmoeglichkeiten')">Einkehrmöglichkeiten</button>
                <div id="einkehrmoeglichkeiten-content" class="content">
                    <p>Hier werden einige Einkehrmöglichkeiten aufgelistet.</p>
                </div>

                <!-- Rezensionen -->
                <button class="btn btn-light" onclick="toggleContent('rezensionen')">Rezensionen</button>
                <div id="rezensionen-content" class="content">
                    <p>Hier sind Rezensionen zum Hike.</p>
                </div>
            </div>
        </div>
</div>





<!-- JavaScript-Bibliotheken -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"></script>
</body>
</html>