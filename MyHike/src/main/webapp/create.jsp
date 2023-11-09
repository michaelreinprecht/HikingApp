<%@ page import="myHikeJava.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="facade.JPAFacade" %>
<%@ page import="models.Month" %>
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
    <!-- Link to create.css -->
    <link rel="stylesheet" href="css/create.css">
    <!-- Link to create.js -->
    <script src="js/create.js"></script>
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
                <a class="nav-link" href="#">Routes</a>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="#">Disabled</a>
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
            <input type="text" id="region" name="region">
        </div>
        <div style="clear:both;">
            <label for="startLocation" style="display: inline-block; width: 150px; font-weight: bold">Start Location:</label>
            <input type="text" id="startLocation" name="startLocation">
        </div>
        <div style="clear:both;">
            <label for="endLocation" style="display: inline-block; width: 150px; font-weight: bold">End Location:</label>
            <input type="text" id="endLocation" name="endLocation">
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
            <input type="text" id="duration" name="duration">
        </div>

        <!-- Generate month input -->
        <div id="months" style="clear:both;">
            <label style="display: inline-block; width: 150px; font-weight: bold">Recommended Months:</label>
            <%
                JPAFacade facade = new JPAFacade();
                List<Month> months = facade.getAllMonths();

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
            <input type="radio" id="<%=i%>-difficulty-rating" name="difficulty-rating" value="<%=i%>" minlength="1">
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

        <div style="display: inline-block; width: 150px; font-weight: bold">
            <label for="fileToUpload" class="form-label">File to upload:</label>
            <input class="form-control" type="file" id="fileToUpload" name="fileToUpload" onchange="displayImage()"/>
        </div>

        <div style="clear:both; margin-left: 280px;">
            <input type="submit" value="submit" onclick="if(!this.form.checkbox.checked){alert('You must agree to the terms first.');return false}">
        </div>
    </form>

</div>
<script type="text/javascript">

    function validateForm() {
        const namePattern = /^[A-Z0-9].*$/;
        const name = document.getElementById("name");
        const regionPattern = /^[A-Z][a-zA-Z ,.'-]+$/;
        const region = document.getElementById("region");
        const startPattern = /^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?),\s*[-+]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$/;
        const start = document.getElementById("startLocation");
        const endPattern = /^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?),\s*[-+]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$/;
        const end = document.getElementById("endLocation");
        const descriptionPattern = /^[A-Z].*$/;
        const description = document.getElementById("description");
        const altitudePattern = /^[1-9][0-9]*$/;
        const altitude = document.getElementById("altitude");
        const distancePattern = /^[1-9]+,[0-9]+$/;
        const distance = document.getElementById("distance");
        const durationPattern = /^[1-9]+,[0-9]+$/
        const duration = document.getElementById("duration");
        const radioStrength = document.querySelector('input[name="strength-rating"]:checked');
        const radioStamina = document.querySelector('input[name="stamina-rating"]:checked');
        const radioDifficulty = document.querySelector('input[name="difficulty-rating"]:checked');
        const radioLandscape = document.querySelector('input[name="landscape-rating"]:checked');

        let check = false;

        const checkboxes = document.getElementsByName("form-check-input[]");
            for (let i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].checked) {
                    if (!namePattern.test(name.value)){
                        alert("Please select a name, it should start with capital letter");
                        return false;
                    }
                    if (!regionPattern.test(region.value)){
                        alert("Please select a region name");
                        return false;

                    }
                    if (!startPattern.test(start.value)){
                        alert("Please write a start location with this pattern:\n" +
                            "latitude , longitude")
                        return false;
                    }
                    if (!endPattern.test(end.value)){
                        alert("Please write a destination with this pattern:\n" +
                            "latitude , longitude")
                        return false;
                    }
                    if (!descriptionPattern.test(description.value)){
                        alert("Please write a description");
                        return false;
                    }
                    if (!altitudePattern.test(altitude.value)){
                        alert("Please write approximately the altitude");
                        return false;
                    }
                    if (!distancePattern.test(distance.value)){
                        alert("Please write a distance");
                        return false;
                    }
                    if (!durationPattern.test(duration.value)){
                        alert("Please write a duration");
                        return false;
                    }
                    if (!radioLandscape) {
                        alert("Please rate landscape.");
                        return false;
                    }

                    if (!radioStrength) {
                        alert("Please rate strength.");
                        return false;
                    }

                    if (!radioStamina) {
                        alert("Please rate stamina.");
                        return false;
                    }
                    if (!radioDifficulty) {
                        alert("Please rate difficulty.");
                        return false;
                    }

                    check = true;
                    break;
                }
            }


        if (!check) {
            alert("Select a month or months");
            return false;
            } else {
            return true;
        }
    }
</script>
</body>
</html>
