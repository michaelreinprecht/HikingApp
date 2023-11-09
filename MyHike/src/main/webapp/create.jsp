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
    <form action="createHikeServlet" method="get" style="margin-left: 10px">
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
            <input type="radio" id="<%=i%>-difficulty-rating" name="difficulty-rating" value="<%=i%>">
            <label for="<%=i%>-difficulty-rating" class="difficulty-rating">
                <i class="fas fa-star d-inline-block"></i>
            </label>
            <%
                }
            %>
        </div><br>

        <div style="clear:both; margin-left: 280px;">
            <input type="submit" value="create">
        </div>
    </form>

</div>
</body>
</html>