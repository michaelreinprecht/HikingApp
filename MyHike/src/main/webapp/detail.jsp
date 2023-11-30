<%@ page import="myHikeJava.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="models.Month" %>
<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="models.PointOfInterest" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%
    //Get the hike which is going to be displayed in detail in this page.
    String id = request.getParameter("Id");
    Hike hike = Database.getHikeById(id);
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=hike.getHikeName()%>
    </title>

    <!-- Bootstrap link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Font Awesome Icons link -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

    <!-- Bootstrap imports -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>

    <!-- jQuery import -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <!-- Link to css files -->
    <link rel="stylesheet" href="css/global.css">
    <link rel="stylesheet" href="css/detail.css">

    <!-- Link to detail.js -->
    <script src="js/detail.js"></script>

    <!-- Leaflet import -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
          integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY="
          crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
            integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo="
            crossorigin=""></script>
    <script src="jetbrains://idea/navigate/reference?project=Database.java&path=node_modules/polyline-encoded/Polyline.encoded.js"></script>
</head>
<body>
<!-- Navigation bar -->
<nav class="navbar sticky-top navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="index.jsp">
        <img src="images/icon3.png" alt="MyHike" class="icon">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02"
            aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="index.jsp">Discover</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="create.jsp">Create Hike</a>
            </li>
        </ul>
    </div>
</nav>

<!-- Hike name -->
<div class="name">
    <h3 class="text-center-name"><%= hike.getHikeName() %>
    </h3>
</div>

<!-- This alert will be displayed if the database delete fails -->
<%
    String error = request.getParameter("error");
    if (error != null && !error.isEmpty()) {
%>
<div id="databaseAlert" class="alert alert-danger row-md" role="alert"
     style="clear:both; margin-bottom: 10px; margin-top: 10px;">
    Database error: <%= error %>
</div>
<%
    }
%>

<!-- Edit button -->
<!-- Buttons Container -->
<div class="container">
    <div class="row">
        <!-- Edit Button -->
        <div class="col-md-6 text-left">
            <a href="edit.jsp?Id=<%=hike.getHikeId()%>" class="btn btn-warning">Edit</a>
        </div>

        <!-- Delete Button -->
        <div class="col-md-6 text-right">
            <form id="deleteForm" action="softDeleteHikeServlet?Id=<%=hike.getHikeId()%>" method="post"
                  enctype="multipart/form-data">
                <button type="submit" id="deleteButton" class="btn btn-danger">Delete</button>
            </form>
        </div>
    </div>
</div>


<!-- Hike Details -->
<div class="container">

    <div class="row">
        <div class="characteristics">
            <div class="group">
                <img src="images/uhr_dauer.png" alt="uhr" class="icons">
                <h5 class="text-center">
                    <% //Null-Value check, if there is no duration we will instead just display a question mark
                        // (TODO generate duration automatically if it has no value)
                        if (hike.getHikeDuration() != null) {
                            LocalTime localTime = hike.getHikeDuration().toLocalTime();
                            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
                            String formattedTime = localTime.format(outputFormatter);
                    %>
                    <%= formattedTime%> hours
                    <%
                    } else {
                    %>
                    ? hours
                    <%
                        }
                    %>
                </h5>
            </div>
            <div class="group">
                <img src="images/streckenlänge.png" alt="streckenlänge" class="icons">
                <h5 class="text-center">
                    <% //Null-Value check, if there is no distance we will instead just display a question mark
                        // (TODO generate distance automatically if it has no value)
                        if (hike.getHikeDistance() != null) {
                    %>
                    <%= hike.getHikeDistance()%>km
                    <%
                    } else {
                    %>
                    ?km
                    <%
                        }
                    %>
                </h5>
            </div>
            <div class="group">
                <img src="images/altitude_icon.png" alt="altitude" class="icons">
                <h5 class="text-center">
                    <% //Null-Value check, if there is no distance we will instead just display a question mark
                        // (TODO generate distance automatically if it has no value)
                        if (hike.getHikeAltitude() != null) {
                    %>
                    <%= hike.getHikeAltitude()%>m
                    <%
                    } else {
                    %>
                    ?m
                    <%
                        }
                    %>
                </h5>
            </div>
            <div class="group">
                <img src="images/region.png" alt="region" class="icons">
                <h5 class="text-center">
                    <%= hike.getHikeRegion().getRegionName()%>
                </h5>
            </div>
            <div class="group">
                <img src="images/months_icon.png" alt="monate" class="icons">
                <h5 class="text-center">
                    <%
                        String[] recommended = Month.getMonthsByBitmap(hike.getHikeMonths());
                        //TODO explain what is being generated
                        for (String rec : recommended) {
                            if (rec != null) {
                    %>
                    <%=rec%>
                    <%
                            }
                        }
                    %>
                </h5>
            </div>
        </div>
        <div class="images">
            <div class="image-container">
                <!-- Rundgangsbild -->
                <img alt="<%=hike.getHikeName()%>" src="data:image/png;base64,<%=hike.getHikeImage()%>"
                     class="hikeImage">
            </div>
            <div class="image-container">
                <!-- Karte -->
                <div id="map" style="height: 100%; width: 100%;"></div>
                <script>
                    // Create a new Leaflet map object
                    //Start coordinates are focused on Vorarlberg
                    let myMap = L.map('map').setView([47.21329, 9.95118], 9);
                    // Add a tile layer from OpenStreetMap
                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        attribution: '© OpenStreetMap contributors'
                    }).addTo(myMap);

                    // Use the Geolocation API to center the map on the user's location
                    myMap.locate({setView: true, maxZoom: 16});

                    // Array to store markers
                    let markers = [];

                    //Holds the last drawn hiking route.
                    let route;

                    let yourLocationIcon = L.icon({
                        iconUrl: 'images/your-location-icon.png',
                        iconSize: [50, 50],
                        iconAnchor: [25, 50],
                        popupAnchor: [0, -50]
                    });

                    // Define a function to handle location found event
                    function onLocationFound(e) {
                        L.marker(e.latlng).setIcon(yourLocationIcon).addTo(myMap).bindPopup("You are here!");
                    }

                    // Listen for the location found event
                    // If user allows us to, we will set the location to his current location.
                    myMap.on('locationfound', onLocationFound);

                    function onMapClick(e) {
                        // Add a marker at the clicked location with the popup
                        let marker = L.marker(e.latlng, { draggable: true }).addTo(myMap).on('click', onMarkerClick);

                        // Store the marker in the array
                        markers.push(marker);
                        displayHikingRoute(markers);
                        setMarkerIcons(markers);

                        // Listen for dragend event to update route when the marker is dragged
                        marker.on('dragend', function () {
                            displayHikingRoute(markers);
                        });
                    }


                    // Listen for the 'click' event for general map clicks
                    myMap.on('click', onMapClick);

                    // Define a function to handle marker click event
                    function onMarkerClick(e) {
                        // Remove the clicked marker from the map
                        myMap.removeLayer(e.target);
                        // Remove the marker from the array
                        markers = markers.filter(marker => marker !== e.target);
                        displayHikingRoute(markers);
                        setMarkerIcons(markers);
                    }

                    // Listen for the 'click' event for marker clicks
                    myMap.on('click', onMarkerClick);



                    //Sets first marker icon to start-icon, last to finish-icon and all other to waypoint-icon.
                    function setMarkerIcons(markers) {
                        let startIcon = L.icon({
                            iconUrl: 'images/start-icon.png',
                            iconSize: [32, 32],
                            iconAnchor: [16, 32],
                            popupAnchor: [0, -32]
                        });
                        let finishIcon = L.icon({
                            iconUrl: 'images/finish-icon.png',
                            iconSize: [32, 32],
                            iconAnchor: [16, 32],
                            popupAnchor: [0, -32]
                        });
                        let waypointIcon = L.icon({
                            iconUrl: 'images/waypoint-icon.png',
                            iconSize: [32, 32],
                            iconAnchor: [16, 32],
                            popupAnchor: [0, -32]
                        });

                        markers.forEach((x) => x.setIcon(waypointIcon));
                        if (markers[0] != null) {
                            markers[0].setIcon(startIcon);
                        }
                        if (markers.length > 1) {
                            markers[markers.length-1].setIcon(finishIcon);
                        }
                    }

                    //Displays the hiking route for the given markers. Based on routing by openroute service API.
                    function displayHikingRoute(markers) {
                        // OpenRouteService API request
                        let url = "https://api.openrouteservice.org/v2/directions/foot-hiking/geojson";
                        let apiKey = "5b3ce3597851110001cf624862d0477273784a3cb757ffdea96c0fff";


                        let coordinates = [];
                        markers.forEach((marker) => {
                            coordinates.push([marker._latlng.lng, marker._latlng.lat]);
                        });

                        let body = {
                            coordinates: coordinates,
                            continue_straight: true
                        };

                        if (coordinates.length >= 2) {
                            fetch(url, {
                                method: 'POST',
                                headers: {
                                    'Accept': 'application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8',
                                    'Content-Type': 'application/json',
                                    'Authorization': apiKey,
                                },
                                body: JSON.stringify(body)
                            })
                                .then(response => response.json())
                                .then(data => {
                                    console.log('Response:', data);

                                    // Extract the route geometry
                                    let routeCoordinates = data.features[0].geometry.coordinates;
                                    let inverted = [];
                                    routeCoordinates.forEach((coordinate) => {
                                        inverted.push([coordinate[1], coordinate[0]]);
                                    });

                                    // Display the route on the map
                                    if (route != null) {
                                        myMap.removeLayer(route);
                                    }
                                    route = L.polyline(inverted, {color: 'black'}).addTo(myMap);
                                })
                                .catch(error => {
                                    console.error('Error:', error);
                                });
                        }
                    }
                </script>
            </div>
        </div>


        <!-- Buttons Container -->
        <div class="button-group">
            <!-- Beschreibung -->
            <button class="btn btn-light" onclick="toggleContent('description')">Description</button>
            <div id="description-content" class="content">
                <p>
                    <%=hike.getHikeDescription()%>
                </p>
            </div>

            <!-- Streckeneigenschaften -->
            <button class="btn btn-light" onclick="toggleContent('hikeProperty')">Hike properties
            </button>
            <div id="hikeProperty-content" class="content">
                <div class="ratings-container">
                    <div class="rating-label"><b>Landscape:</b></div>
                    <%
                        int i; //Declare variable for loops -> also used in further loops.
                        int landscapeRating = hike.getHikeLandscape();
                        //Display a number of "active" and "inactive" stars, depending on the landscapeRating
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

                    <div class="rating-label"><b>Strength:</b></div>
                    <%
                        int strengthRating = hike.getHikeStrength();
                        //Display a number of "active" and "inactive" stars, depending on the strengthRating
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

                    <div class="rating-label"><b>Stamina:</b></div>
                    <%
                        int staminaRating = hike.getHikeStamina();
                        //Display a number of "active" and "inactive" stars, depending on the staminaRating
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

                    <div class="rating-label"><b>Overall Difficulty:</b></div>
                    <%
                        int difficultyRating = hike.getHikeDifficulty();
                        //Display a number of "active" and "inactive" stars, depending on the difficultyRating
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
            </div>

            <!-- Points of Interest -->
            <button class="btn btn-light" onclick="toggleContent('pointsOfInterest')">Points of Interest</button>
            <div id="pointsOfInterest-content" class="content" style="padding: 10px">
                <h4 style="margin-top: 10px">Add new Points of Interest:</h4>
                <form id="myForm" enctype="multipart/form-data">
                    <div class="row" style="padding: 10px">
                        <div class="col-md-4" style="margin: 0; padding: 0">
                            <!-- Your input fields here -->
                            <div class="form-group">
                                <label style="text-align: start; width: 100%;" class="labels"
                                       for="poiTitle">Title:</label>
                                <input class="form-control" type="text" id="poiTitle"
                                       placeholder="Your point of interests title ..."
                                       maxlength="40"
                                       required/>
                            </div>
                            <div class="form-group">
                                <label style="text-align: start; width: 100%;" class="labels" for="poiDescription">Description:</label>
                                <input class="form-control w-100" type="text" id="poiDescription"
                                       placeholder="Your point of interests description ..."
                                       maxlength="150"
                                       required/>
                            </div>
                            <div class="form-group">
                                <label style="text-align: start; width: 100%;" class="labels"
                                       for="poiLon">Longitude:</label>
                                <input class="form-control w-100" type="text" id="poiLon" placeholder="12.3456"/>
                            </div>
                            <div class="form-group">
                                <label style="text-align: start; width: 100%;" class="labels"
                                       for="poiLat">Latitude:</label>
                                <input class="form-control w-100" type="text" id="poiLat" placeholder="12.3456"/>
                            </div>
                        </div>
                        <div class="col-md-8 d-flex flex-column">

                            <div style="max-height: 100%; margin: 1rem">
                                <img id="imgDisplay" alt="" src=""
                                     style="max-width: 100%; max-height: 250px; object-fit: contain;">
                            </div>
                            <div style="margin-bottom: 1rem; margin-top: auto">
                                <input class="form-control" type="file" id="poiImage" name="poiImage"
                                       onchange="displayImage()"/>
                            </div>

                        </div>
                    </div>
                    <div style="margin-top: 30px">
                        <button id="addPOIButton" type="button" class="btn btn-success" style="width: 100%;"
                                data-hike-id="<%=hike.getHikeId()%>">Add
                        </button>
                        <br>
                        <!-- This alert will be displayed if (for example), validation is not passed. -->
                        <div id="validationAlert" class="alert alert-danger row-md" role="alert"
                             style="clear:both; display: none; margin-bottom: 10px; margin-top: 10px;"></div>
                        <div id="loadingDiv" style="display: none; margin-top: 10px; margin-bottom: 10px">
                            <img src="images/loading.gif" style="height: 40px; width: 40px" alt="Loading..."/>
                        </div>
                        <!-- This alert will be displayed once a POI has been successfully added. -->
                        <div id="successAlert" class="alert alert-success row-md" role="alert"
                             style="clear:both; display: none; margin-bottom: 10px; margin-top: 10px;">
                            Your point of interest has been successfully added.
                        </div>
                    </div>
                </form>
                <div id="result" style="display: flex; flex-wrap: wrap; gap: 1%;">
                    <%
                        List<PointOfInterest> pointsOfInterest = hike.getHikePointsOfInterest();
                        for (PointOfInterest poi : pointsOfInterest) {
                            String image = poi.getPointOfInterestImage();
                    %>
                    <tags:card
                            id="<%=poi.getPointOfInterestId()%>"
                            title="<%=poi.getPointOfInterestName()%>"
                            description="<%=poi.getPointOfInterestDescription()%>"
                            lon="<%=poi.getPointOfInterestLon().toString()%>"
                            lat="<%=poi.getPointOfInterestLat().toString()%>"
                            src="<%=image%>"
                            poiId="<%=poi.getPointOfInterestId()%>"/>
                    <%
                        }
                    %>
                </div>
            </div>

            <!-- Rezensionen -->
            <button class="btn btn-light" onclick="toggleContent('review')">Reviews</button>
            <div id="review-content" class="content">
                <!-- TODO Rezensionen generieren -->
                <p>Here are some reviews of this hike.</p>
            </div>
        </div>
    </div>
</div>
</div>
<!-- Bootstrap imports -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>