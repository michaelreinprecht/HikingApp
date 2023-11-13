<%@ page import="models.Month" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Region" %>
<%@ page import="myHikeJava.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>

    <!-- Bootstrap link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Font Awesome Icons link -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

    <!-- Link to edit.css -->
    <link rel="stylesheet" href="css/edit.css">

    <!-- Link to edit.js -->
    <script src="js/edit.js"></script>
</head>
<body>
<!-- Navigation bar -->
<nav class="navbar sticky-top navbar-expand-lg navbar-dark" style="background-color: #07773a; height: 80px">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02"
            aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="index.jsp">Discover</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="create.jsp">Create Hike <span class="sr-only">(current)</span></a>
            </li>
        </ul>
    </div>
</nav>

<%
    //Get the hike which is going to be displayed in for editing in this page.
    String id = request.getParameter("Id");
    Hike hike = Database.getHikeById(id);
%>

<div class="container-fluid" style="background-color: white; padding: 0">
    <form action="createHikeServlet" method="post" style="margin-left: 10px" enctype="multipart/form-data" onsubmit="return validateForm();">
        <br>
        <div style="clear:both;">
            <label for="name" style="display: inline-block; width: 150px; font-weight: bold">Name:</label>
            <input type="text" id="name" name="name" placeholder="Your Hike's name ..." value="<%=hike.getHikeName()%>" required>
        </div>
        <div style="clear:both;">
            <label for="description" style="display: inline-block; width: 150px; font-weight: bold">Beschreibung:</label>
            <textarea id="description" name="description" placeholder="Your description ..."><%=hike.getHikeDescription()%></textarea>
        </div>
        <div style="clear:both;">
            <label for="region" style="display: inline-block; width: 150px; font-weight: bold">Region:</label>
            <select name="region" id="region">
                <%
                    List<Region> regions = Database.getAllRegions();
                    for(Region region: regions) {
                        if (Objects.equals(hike.getHikeRegion().getRegionName(), region.getRegionName())) {
                %>
                <!-- If the Region of hike and current Region element match, set this as the selected option -->
                <option value="<%=region.getRegionName()%>" selected><%=region.getRegionName()%></option>
                <%       } else {
                %>
                <option value="<%=region.getRegionName()%>"><%=region.getRegionName()%></option>
                <%
                        }
                    }
                %>
            </select>
            <!-- <input type="text" id="region" name="region"> -->
        </div>
        <div style="clear:both;">
            <label style="display: inline-block; width: 150px; font-weight: bold">Start Location:</label><br>
            <label for="startLon" style="display: inline-block; width: 150px;">Lon: </label><input type="text" id="startLon" name="startLon" placeholder="12.3456" value="<%=hike.getHikeStartLon()%>" required><br>
            <label for="startLat" style="display: inline-block; width: 150px;">Lat: </label><input type="text" id="startLat" name="startLat" placeholder="12.3456" value="<%=hike.getHikeStartLat()%>" required>
        </div>
        <div style="clear:both;">
            <label style="display: inline-block; width: 150px; font-weight: bold">End Location:</label><br>
            <label for="endLon" style="display: inline-block; width: 150px;">Lon: </label><input type="text" id="endLon" name="endLon" placeholder="12.3456" value="<%=hike.getHikeEndLon()%>" required><br>
            <label for="endLat" style="display: inline-block; width: 150px;">Lat: </label><input type="text" id="endLat" name="endLat" placeholder="12.3456" value="<%=hike.getHikeEndLat()%>" required>
        </div>
        <div style="clear:both;">
            <label for="altitude" style="display: inline-block; width: 150px; font-weight: bold">Altitude (in meters):</label>
            <input type="text" id="altitude" name="altitude" value="<%=hike.getHikeAltitude()%>">
        </div>
        <div style="clear:both;">
            <label for="distance" style="display: inline-block; width: 150px; font-weight: bold">Distance (in kilometers):</label>
            <input type="text" id="distance" name="distance" value="<%=hike.getHikeDistance()%>">
        </div>
        <div style="clear:both;">
            <label for="duration" style="display: inline-block; width: 150px; font-weight: bold">Duration (in hours:minutes):</label>
            <input type="time" id="duration" name="duration" value="<%=hike.getHikeDuration()%>">
        </div>

        <!-- Generate month input -->
        <div id="months" style="clear:both;">
            <label style="display: inline-block; width: 150px; font-weight: bold">Recommended Months:</label>
            <%
                List<Month> months = Database.getAllMonths();
                for(Month month: months) {
            %>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" value="<%=month.getMonthId()%>" name="months" id="<%=month.getMonthName()%>">
                <label class="form-check-label" for="<%=month.getMonthName()%>"><%=month.getMonthName()%></label>
            </div>
            <%
                }
            %>
        </div><br>

        <!-- Generate star rating input -->
        <label style="display: inline-block; width: 150px; font-weight: bold">Landscape:</label>
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

        <label style="display: inline-block; width: 150px; font-weight: bold">Strength:</label>
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

        <label style="display: inline-block; width: 150px; font-weight: bold">Stamina:</label>
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

        <label style="display: inline-block; width: 150px; font-weight: bold">Difficulty:</label>
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
        </div><br>

        <div>
            <img id="uploadedImage" style="max-width: 100%; max-height: 200px; margin-top: 20px;" />
        </div>

        <div class="row-md" style="width: 150px; font-weight: bold">
            <label for="fileToUpload" class="form-label">Image upload:</label>
            <input class="form-control" type="file" id="fileToUpload" name="fileToUpload" onchange="displayImage()"/>
        </div>

        <!-- This alert will be displayed if (for example), validation is not passed -->
        <div id="validationAlert" class="alert alert-danger row-md" role="alert" style="clear:both; display: none; margin-bottom: 10px; margin-top: 10px;"></div>
        <!-- This alert will be displayed if the database upload fails even though validation was passed, or if no valid image was uploaded -->
        <%
            String error = request.getParameter("error");
            if (error != null && !error.isEmpty()) {
        %>
        <div id="databaseAlert" class="alert alert-danger row-md" role="alert" style="clear:both; margin-bottom: 10px; margin-top: 10px;">
            Database error: <%= error %>
        </div>
        <%
            }
        %>

        <div class="row-md" style="clear:both; margin-left: 280px;">
            <button type="submit" class="btn btn-success" style="width: 10%">Submit</button>
        </div>
    </form>

</div>

<!-- Bootstrap imports -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>