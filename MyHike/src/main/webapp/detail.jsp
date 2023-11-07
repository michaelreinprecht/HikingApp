<%@ page import="com.example.myhike.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="com.example.myhike.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="com.example.myhike.StarRatingGenerator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hike Details</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="detail.css">
    <style>
        .content {
            display: none;
            background-color: white;
            padding: 50px;
            margin-bottom: 10px;
        }
        .container {
            display: flex;
            justify-content: space-between;
        }
        .col-md-6 {
            flex: 0 0 48%; /* Anpassen der Breite für die Bilder */
        }
        .text-center {
            text-align: left;
        }
        .button-group {
            display: flex;
            flex-direction: column;


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
        <img src="images/icon3.png" width="90" height="80" alt="MyHike">
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

<!-- Hike Details -->
<div class="container">
    <div class="col-md-6">
        <%
            String id = request.getParameter("Id");
            Hike hike = Database.getHikeById(Integer.parseInt(id));
        %>
        <h3 class="text-center" style="color: green; font-style: italic"><%= hike.getHikeName() %></h3>

        <!-- Rundgangsbild -->
        <img src="images/beispiel_berge.jpg" alt="Rundgangsbild" style="width: 100%; height: 350px; margin-top: 20px; margin-bottom: 20px;">

        <!-- Buttons -->
        <div class="button-group" style=" width: 210%">

            <!-- Beschreibung -->
            <button class="btn btn-light" onclick="toggleContent('beschreibung')">Beschreibung</button>
                <div id="beschreibung-content" class="content">
                    <p>Hier ist die Beschreibung des Hikes.</p>
                </div>

            <!-- Streckeneigenschaften -->
            <button class="btn btn-light" onclick="toggleContent('streckeneigenschaften')">Streckeneigenschaften</button>
                <div id="streckeneigenschaften-content" class="content">
                    <p><b>Landscape:</b> <%= StarRatingGenerator.generateStarRating(5, hike.getHikeLandscape()) %></p>
                    <p><b>Strength:</b> <%= StarRatingGenerator.generateStarRating(5, hike.getHikeStrength()) %></p>
                    <p><b>Stamina:</b> <%= StarRatingGenerator.generateStarRating(5, hike.getHikeStamina()) %></p>
                    <p><b>Overall Difficulty:</b> <%= StarRatingGenerator.generateStarRating(5, hike.getHikeDifficulty()) %></p>
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
    <div class="col-md-6">
        <!-- Karte -->
        <img src="images/map.png" alt="Karte" style="width: 100%; height: 350px; margin-top: 60px; margin-bottom: 20px;">
    </div>
</div>

<!-- JavaScript-Bibliotheken -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"></script>
</body>
</html>