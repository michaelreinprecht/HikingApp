<%@ page import="database.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="models.Month" %>
<%@ page import="models.PointOfInterest" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Comment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%
    //Get the hike which is going to be displayed in detail in this page.
    String id = request.getParameter("Id");
    Hike hike = Database.getHikeById(id);

    boolean loggedIn = session.getAttribute("username") != null;
    boolean ownsHike = loggedIn && (hike.getHikeOfUser() != null) && hike.getHikeOfUser().getUserName().equals(session.getAttribute("username"));
    boolean isAdmin = session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin");
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

    <!-- Google font link -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Barlow&display=swap" rel="stylesheet">

    <!-- jQuery import -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <!-- Bootstrap imports -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>

    <!-- Link to css files -->
    <link rel="stylesheet" href="css/detail.css">

    <!-- Leaflet import -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
          integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY="
          crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
            integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo="
            crossorigin=""></script>

    <!-- Leaflet Routing Machine CSS -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet-routing-machine/dist/leaflet-routing-machine.css" />

    <!-- Leaflet Routing Machine JS -->
    <script src="https://unpkg.com/leaflet-routing-machine/dist/leaflet-routing-machine.js"></script>

    <!-- Link to detail.js -->
    <script src="js/detail.js"></script>
    <script src="js/detailMap.js"></script>
</head>
<body>
<!-- Navigation bar -->
<nav class="navbar sticky-top navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="discover.jsp">
        <img src="images/icon3.png" alt="MyHike" class="icon">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02"
            aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="discover.jsp">Discover</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="create.jsp">Create Hike</a>
            </li>
        </ul>
        <ul class="navbar-nav">
            <li class="nav-item">
                <%if (!loggedIn) { %>
                <a class="nav-link" href="login.jsp">Login</a>
                <% } else { %>
                <a class="nav-link" href="logoutServlet"><%=session.getAttribute("username")%><br>Logout</a>
                <% } %>
            </li>
        </ul>
    </div>
</nav>

<!-- Hike name -->
<!-- Display successAlert based on successAlert parameter. -->
<%
    String successAlert = request.getParameter("successAlert");
    String error = request.getParameter("error");
%>
<tags:multiAlert alert='<%=successAlert%>' error="<%=error%>"/>



<!-- Edit button -->
<!-- Buttons Container -->
<div class="container">
    <div class="row" style="margin-top: 40px; width: 100%">
        <!-- Edit Button -->
        <div class="col-md-2">
            <%
                if (!loggedIn || (!ownsHike && !isAdmin)) {
            %>
            <a href="edit.jsp?Id=<%=hike.getHikeId()%>" class="btn btn-warning">Edit</a>
            <%
            }
            %>
        </div>

        <div class="col-md-8">
            <h3><%=hike.getHikeName() %>
            </h3>
        </div>

        <!-- Print Button -->
        <div class="col-md-1">
            <button type="button" id="printButton" class="btn btn-info" onclick="printPage()">
                <img src="images/print-icon.png" height="24px" alt="Print">
            </button>
        </div>

        <!-- Delete Button -->
        <div class="col-md-1">
            <%
                if (!loggedIn || (!ownsHike && !isAdmin)) {
            %>
            <form id="deleteForm" action="softDeleteHikeServlet?Id=<%=hike.getHikeId()%>" method="post"
                  enctype="multipart/form-data">
                <button type="submit" id="deleteButton" class="btn btn-danger">Delete</button>
            </form>
            <%
                }
            %>
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
                <h5 class="text-center" id="distance-container"></h5>
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
                        if (recommended != null) {
                            for (String rec : recommended) {
                                if (rec != null) {
                    %>
                    <%=rec%>
                    <%
                                }
                            }
                        }
                    %>
                </h5>
            </div>
        </div>
        <div class="images">
            <div class="image-container">
                <!-- Rundgangsbild -->
                <img alt="<%=hike.getHikeName()%>" src="<%=hike.getHikeImage() != null ? "data:image/png;base64," + hike.getHikeImage() : ""%>"
                     class="hikeImage">
            </div>
            <div class="image-container">
                <!-- Karte -->
                <div id="map" style="height: 100%; width: 100%;" data-route-coordinates="<%=hike.getHikeRouteCoordinates()%>"></div>
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

            <!-- Rezensionen -->
            <button class="btn btn-light" onclick="toggleContent('review')">Reviews</button>
            <div id="review-content" class="content">
                <form method="post" action="addCommentServlet?hikeId=<%=hike.getHikeId()%>">
                    <div class="row">
                        <textarea style="width: 100%; padding: 10px;" name="commentDescription" id="commentDescription"
                                  placeholder="Enter your comment here ..."></textarea>
                    </div>
                    <div class="row">
                        <div class="col-md ml-auto" style="text-align: right; margin-top: 10px; margin-bottom:10px;">
                            <button type="submit" class="btn btn-success">Add comment</button>
                        </div>
                    </div>
                </form>
                <div class="row">
                    <%
                        List<Comment> comments = hike.getHikeComments();
                        if (comments == null || comments.isEmpty()) {
                    %>
                    <p>Here are some reviews of this hike.</p>
                    <%
                    } else {
                            //Iterate through comments list backwards, this way newest comments show up first
                            for (i = comments.size()-1; i >= 0; i--) {
                    %>
                    <div class="comment-card">
                        <div class="row" style="width: 100%">
                            <div class="col-md-11" style="text-align: left">
                                <label class="labels"><%=comments.get(i).getCommentUser().getUserName()%>
                                </label><br>
                                <label><%=comments.get(i).getCommentDescription()%>
                                </label>
                            </div>
                            <div class="col-md-1 ml-auto d-flex align-items-center">
                                <a href="deleteCommentServlet?hikeId=<%=hike.getHikeId()%>&commentId=<%=comments.get(i).getCommentId()%>">
                                    <img src="images/trash-icon.png" alt="Delete" style="height: 25px;">
                                </a>
                            </div>
                        </div>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>
            </div>

            <!-- Points of Interest -->
            <button class="btn btn-light" onclick="toggleContent('pointsOfInterest')">Points of Interest</button>
            <div id="pointsOfInterest-content" class="content" style="padding: 10px">
                <%
                    if (!loggedIn || (!ownsHike && !isAdmin)) {
                %>
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
                <%
                    }
                %>
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