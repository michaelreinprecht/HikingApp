<%@ page import="models.Month" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Region" %>
<%@ page import="database.Database" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Create</title>
    <!-- Bootstrap link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Font Awesome Icons link -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

    <!-- Google font link -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Barlow&display=swap" rel="stylesheet">

    <!-- Link to css files -->
    <link rel="stylesheet" href="css/create.css">

    <!-- JQuery import -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <!-- Leaflet import -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
          integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY="
          crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
            integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo="
            crossorigin=""></script>

    <!-- Link to create_edit.js -->
    <script src="js/create_edit.js"></script>
    <script src="js/editMap.js"></script>
</head>
<%
    if ((request.getSession(false).getAttribute("username") == null)){
        %>
<jsp:forward page="login.jsp"/>
<%
    }
%>
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
            <li class="nav-item active">
                <a class="nav-link" href="create.jsp">Create Hike <span class="sr-only">(current)</span></a>
            </li>
            <%if (session.getAttribute("username") != null) { %>
            <li class="nav-item">
                <a class="nav-link" href="createdHikes.jsp">Your Hikes</a>
            </li>
            <% } %>
        </ul>
        <ul class="navbar-nav">
            <li class="nav-item">
                <%if (session.getAttribute("username") == null) { %>
                <a class="nav-link" href="login.jsp">Login</a>
                <% } else { %>
                <a class="nav-link" href="logoutServlet"><%=session.getAttribute("username")%><br>Logout</a>
                <% } %>
            </li>
        </ul>
    </div>
</nav>

<!-- Titel -->
<div class="title">
    <h3 class="text-center">
        Create your Hike
    </h3>
</div>

<form action="createHikeServlet" method="post" enctype="multipart/form-data" onsubmit="return validateForm();">
    <div class="container">
        <div class="row">
            <div id="map" style="height: 400px; width: 100%;" data-route-coordinates=""></div>
            <input id="route-coordinates" name="route-coordinates" hidden="hidden">
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="leftSide">
                    <!-- Name -->
                    <div class="clear">
                        <label for="name" class="labels_withmargin">Name:</label>
                        <input class="form-control w-100" type="text" id="name" name="name" placeholder="Your Hike's name ..." maxlength="100" required>
                    </div>

                    <!-- Region -->
                    <div class="clear">
                        <label for="region" class="labels_withmargin">Region:</label>
                        <select class="form-control w-100" name="region" id="region">
                            <%
                                List<Region> regions = Database.getAllRegions();
                                for(Region region: regions) {
                            %>
                            <option value="<%=region.getRegionName()%>"><%=region.getRegionName()%></option>
                            <%
                                }
                            %>
                        </select>
                    </div>

                    <!-- Altitude-->
                    <div class="clear">
                        <label for="altitude" class="labels_withmargin">Altitude (in meters):</label>
                        <input class="form-control w-100" type="text" id="altitude" name="altitude" placeholder="100" readonly>
                    </div>

                    <!-- Distance-->
                    <div class="clear">
                        <label for="distance" class="labels_withmargin">Distance (in kilometers):</label>
                        <input class="form-control w-100" type="text" id="distance" name="distance" placeholder="1.00" readonly>
                    </div>

                    <!-- Duration-->
                    <div class="clear">
                        <label for="duration" class="labels_withmargin">Duration (in hours:minutes):</label>
                        <input class="form-control w-100" type="time" id="duration" name="duration" value="01:00" readonly>
                    </div>
                </div>
            </div>


            <!-- rechte Hälfte -->
            <div class="col-md-6">
                <div class="rightSide">

                    <!-- Recommended months-->
                    <div id="months" class="clear">
                        <label class="labels_withmargin">Recommended Months:</label>
                        <%
                            String[] months = Month.ALL_MONTHS;
                            for(String month: months) {
                        %>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="<%=month%>" name="months" id="<%=month%>">
                            <label class="form-check-label" for="<%=month%>"><%=month%></label>
                        </div>
                        <%
                            }
                        %>
                    </div><br>

                    <!-- Stars-->
                    <label class="labels">Landscape:</label>
                    <div class="rating-wrapper">
                        <%
                            int maxRating = 5;
                            for(int i = 1; i <= maxRating; i++) {
                        %>
                        <input type="radio" id="<%=i%>-landscape-rating" name="landscape-rating" value="<%=maxRating-(i-1)%>">
                        <label for="<%=i%>-landscape-rating" class="landscape-rating">
                            <i class="fas fa-star d-inline-block"></i>
                        </label>
                        <%
                            }
                        %>
                    </div><br>

                    <label class="labels">Strength:</label>
                    <div class="rating-wrapper">
                        <%
                            for(int i = 1; i <= maxRating; i++) {
                        %>
                        <input type="radio" id="<%=i%>-strength-rating" name="strength-rating" value="<%=maxRating-(i-1)%>">
                        <label for="<%=i%>-strength-rating" class="strength-rating">
                            <i class="fas fa-star d-inline-block"></i>
                        </label>
                        <%
                            }
                        %>
                    </div><br>

                    <label class="labels">Stamina:</label>
                    <div class="rating-wrapper">
                        <%
                            for(int i = 1; i <= maxRating; i++) {
                        %>
                        <input type="radio" id="<%=i%>-stamina-rating" name="stamina-rating" value="<%=maxRating-(i-1)%>">
                        <label for="<%=i%>-stamina-rating" class="stamina-rating">
                            <i class="fas fa-star d-inline-block"></i>
                        </label>
                        <%
                            }
                        %>
                    </div><br>

                    <label class="labels">Difficulty:</label>
                    <div class="rating-wrapper">
                        <%
                            for(int i = 1; i <= maxRating; i++) {
                        %>
                        <input type="radio" id="<%=i%>-difficulty-rating" name="difficulty-rating" value="<%=maxRating-(i-1)%>">
                        <label for="<%=i%>-difficulty-rating" class="difficulty-rating">
                            <i class="fas fa-star d-inline-block"></i>
                        </label>
                        <%
                            }
                        %>


                    </div>

                    <!-- Description-->
                    <div class="clear">
                        <label for="description" class="labels_withmargin">Description:</label>
                        <textarea class="form-control" id="description" name="description" placeholder="Your description ..." maxlength="1000"></textarea>
                    </div>
                    <!-- first Image Upload -->
                    <div class="imageBox">
                        <div>
                            <img id="uploadedImage" alt=""/>
                        </div>
                        <label for="fileToUpload" class="form-label">Image upload:</label>
                        <input class="form-control" type="file" id="fileToUpload" name="fileToUpload" onchange="displayImage()"/>

                    </div>
                </div>
            </div>
        </div>
    </div>


    <div style="background-color: white; padding: 0">
        <br>
        <!-- This alert will be displayed if (for example), validation is not passed -->
        <div id="validationAlert" class="alert alert-danger row-md" role="alert" style="clear:both; display: none; margin-bottom: 10px; margin-top: 10px;"></div>
        <!-- This alert will be displayed if the database upload fails even though validation was passed, or if no valid image was uploaded -->
        <%
            String error = request.getParameter("error");
        %>
        <tags:multiAlert error="<%=error%>"/>

        <div class="row-md" style="clear:both;">
            <div class="col-md-5 offset-md-5 text-right">
                <button type="submit" class="btn btn-success">Submit</button>
            </div>
        </div>
</form>

<!-- Bootstrap imports -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>