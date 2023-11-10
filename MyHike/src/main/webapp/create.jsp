<%@ page import="models.Month" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Region" %>
<%@ page import="myHikeJava.Database" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Detail</title>

    <!-- Bootstrap link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Font Awesome Icons link -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

    <!-- Link to create.css -->
    <link rel="stylesheet" href="css/create.css">

    <!-- Link to create.js -->
    <script src="js/create.js"></script>
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
                <a class="nav-link" href="#">Discover</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Routes</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="index.jsp">Create Hike <span class="sr-only">(current)</span></a>
            </li>
        </ul>
    </div>
</nav>

<div class="container-fluid" style="background-color: white; padding: 0">
    <form action="createHikeServlet" method="post" style="margin-left: 10px" enctype="multipart/form-data" onsubmit="return validateForm();">
        <br>
        <div style="clear:both;">
            <label for="name" style="display: inline-block; width: 150px; font-weight: bold">Name:</label>
            <input type="text" id="name" name="name">
        </div>
        <div style="clear:both;">
            <label for="region" style="display: inline-block; width: 150px; font-weight: bold">Region:</label>
            <select name="region" id="region">
                <%
                    List<Region> regions = Database.getAllRegions();
                    for(Region region: regions) {
                %>
                <option value="<%=region.getRegionName()%>"><%=region.getRegionName()%></option>
                <%
                    }
                %>
            </select>
            <!-- <input type="text" id="region" name="region"> -->
        </div>
        <div style="clear:both;">
            <label style="display: inline-block; width: 150px; font-weight: bold">Start Location:</label><br>
            <label for="startLon" style="display: inline-block; width: 150px;">Lon: </label><input type="text" id="startLon" name="startLon"><br>
            <label for="startLat" style="display: inline-block; width: 150px;">Lat: </label><input type="text" id="startLat" name="startLat">
        </div>
        <div style="clear:both;">
            <label style="display: inline-block; width: 150px; font-weight: bold">End Location:</label><br>
            <label for="endLon" style="display: inline-block; width: 150px;">Lon: </label><input type="text" id="endLon" name="endLon"><br>
            <label for="endLat" style="display: inline-block; width: 150px;">Lat: </label><input type="text" id="endLat" name="endLat">
        </div>
        <div style="clear:both;">
            <label for="description" style="display: inline-block; width: 150px; font-weight: bold">Beschreibung:</label>
            <input type="text" id="description" name="description">
        </div>
        <div style="clear:both;">
            <label for="altitude" style="display: inline-block; width: 150px; font-weight: bold">Altitude:</label>
            <input type="text" id="altitude" name="altitude">
        </div>
        <div style="clear:both;">
            <label for="distance" style="display: inline-block; width: 150px; font-weight: bold">Distance:</label>
            <input type="text" id="distance" name="distance">
        </div>
        <div style="clear:both;">
            <label for="duration" style="display: inline-block; width: 150px; font-weight: bold">Duration:</label>
            <input type="time" id="duration" name="duration">
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
            <input type="radio" id="<%=i%>-landscape-rating" name="landscape-rating" value="<%=i%>">
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
            <input type="radio" id="<%=i%>-strength-rating" name="strength-rating" value="<%=i%>">
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
            <input type="radio" id="<%=i%>-stamina-rating" name="stamina-rating" value="<%=i%>">
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
            <input type="radio" id="<%=i%>-difficulty-rating" name="difficulty-rating" value="<%=i%>">
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
            <label for="fileToUpload" class="form-label">File to upload:</label>
            <input class="form-control" type="file" id="fileToUpload" name="fileToUpload" onchange="displayImage()"/>
        </div>

        <!-- This alert will be displayed if (for example), validation is not passed -->
        <div id="validationAlert" class="alert alert-danger row-md" role="alert" style="clear:both; display: none; margin-bottom: 10px; margin-top: 10px;"></div>
        <!-- TODO This alert will be displayed if the database upload fails even though validation was passed -->
        <div id="databaseAlert" class="alert alert-danger row-md" role="alert" style="clear:both; display: none; margin-bottom: 10px; margin-top: 10px;"></div>


        <div class="row-md" style="clear:both; margin-left: 280px;">
            <button type="submit" class="btn btn-success" style="width: 10%" onclick="if(!this.form.checkbox.checked){alert('You must agree to the terms first.');return false}">Submit</button>
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
<script type="text/javascript">

    function validateForm() {
        const namePattern = /^[A-Z0-9].*$/;
        const name = document.getElementById("name");
        const regionPattern = /^[a-zA-Z0-9 ,.'-]+$/;
        const region = document.getElementById("region");
        const startPattern = /^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?),\s*[-+]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$/;
        const start = document.getElementById("startLocation");
        const endPattern = /^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?),\s*[-+]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$/;
        const end = document.getElementById("endLocation");
        const descriptionPattern = /^[a-zA-Z0-9 ,.'-]+$/;
        const description = document.getElementById("description");
        const altitudePattern = /^[1-9][0-9]*$/;
        const altitude = document.getElementById("altitude");
        const distancePattern = /^[0-9]+(?:,[0-9]?[0-9])?$/;
        const distance = document.getElementById("distance");
        const durationPattern = /^[0-9]+(?:,[0-9]?[0-9])?$/
        const duration = document.getElementById("duration");
        const radioStrength = document.querySelector('input[name="strength-rating"]:checked');
        const radioStamina = document.querySelector('input[name="stamina-rating"]:checked');
        const radioDifficulty = document.querySelector('input[name="difficulty-rating"]:checked');
        const radioLandscape = document.querySelector('input[name="landscape-rating"]:checked');

        const checkboxes = document.getElementsByClassName("form-check-input");
        const validationAlert = document.getElementById("validationAlert");

        let checked = false;
        for (let i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                checked = true;
            }
        }
        if (!checked) {
            validationAlert.innerHTML = "Please pick at least one recommended month.";
            validationAlert.style.display = "block";
            return false;
        }
        if (!namePattern.test(name.value)){
            validationAlert.innerHTML = "Please select a name.";
            validationAlert.display = "block";
            return false;
        }
        if (!regionPattern.test(region.value)){
            validationAlert.innerHTML = "Please select a region.";
            validationAlert.display = "block";
            return false;
        }
        if (!startPattern.test(start.value)){
            validationAlert.innerHTML = "Please enter a valid starting lon-coordinate.";
            validationAlert.display = "block";
            return false;
        }
        if (!endPattern.test(end.value)){
            validationAlert.innerHTML = "Please enter a valid ending lon-coordinate.";
            validationAlert.display = "block";
            return false;
        }
        if (!descriptionPattern.test(description.value)){
            validationAlert.innerHTML = "Please enter a valid description.";
            validationAlert.display = "block";
            return false;
        }
        if (!altitudePattern.test(altitude.value)){
            validationAlert.innerHTML = "Please enter a valid altitude in meters.";
            validationAlert.display = "block";
            return false;
        }
        if (!distancePattern.test(distance.value)){
            validationAlert.innerHTML = "Please enter a valid distance in kilometers.";
            validationAlert.display = "block";
            return false;
        }
        if (!durationPattern.test(duration.value)){
            validationAlert.innerHTML = "Please enter a valid distance in hours.";
            validationAlert.display = "block";
            return false;
        }
        if (!radioLandscape) {
            validationAlert.innerHTML = "Please rate the landscape.";
            validationAlert.display = "block";
            return false;
        }
        if (!radioStrength) {
            validationAlert.innerHTML = "Please rate the required strength for this hike.";
            validationAlert.style.display = "block";
            return false;
        }
        if (!radioStamina) {
            validationAlert.innerHTML = "Please rate the required stamina for this hike.";
            validationAlert.display = "block";
            return false;
        }
        if (!radioDifficulty) {
            validationAlert.innerHTML = "Please rate this hikes difficulty.";
            validationAlert.display = "block";
            return false;
        }
        return true;
    }
</script>
</body>
</html>