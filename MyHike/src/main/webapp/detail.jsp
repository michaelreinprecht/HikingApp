<%@ page import="myHikeJava.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="models.Month" %>
<%@ page import="javax.xml.crypto.Data" %>
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
    <title><%=hike.getHikeName()%></title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Font Awesome Icons -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

    <!-- Bootstrap JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>

    <!-- Your custom stylesheets and scripts -->
    <link rel="stylesheet" href="css/global.css">
    <link rel="stylesheet" href="css/detail.css">
    <script src="js/detail.js"></script>

    <!-- jQuery (again) - added for clarity, in case it was somehow overridden -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
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
<div id="databaseAlert" class="alert alert-danger row-md" role="alert" style="clear:both; margin-bottom: 10px; margin-top: 10px;">
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
            <form id="deleteForm" action="softDeleteHikeServlet?Id=<%=hike.getHikeId()%>" method="post" enctype="multipart/form-data">
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
                    <%  //Null-Value check, if there is no duration we will instead just display a question mark
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
                    <%  //Null-Value check, if there is no distance we will instead just display a question mark
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
                    <%  //Null-Value check, if there is no distance we will instead just display a question mark
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
                        for (String rec: recommended) {
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
                <img alt="<%=hike.getHikeName()%>" src="data:image/png;base64,<%=hike.getHikeImage()%>" class="hikeImage">
            </div>
            <div class="image-container">
                <!-- Karte -->
                <img src="images/map.png" alt="Karte" class="map">
            </div>
        </div>


        <!-- Buttons Container -->
        <div class="button-group">
            <!-- Beschreibung -->
            <button class="btn btn-light" onclick="toggleContent('beschreibung')">Beschreibung</button>
            <div id="beschreibung-content" class="content">
                <p>
                    <%=hike.getHikeDescription()%>
                </p>
            </div>

                <!-- Streckeneigenschaften -->
                <button class="btn btn-light" onclick="toggleContent('streckeneigenschaften')">Streckeneigenschaften
                </button>
                <div id="streckeneigenschaften-content" class="content">
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
                <div id="pointsOfInterest-content" class="content">
                    <!-- Remove this shit, just for testing -->
                    <form id="myForm" onsubmit="sendData()">
                        <!-- Your input fields here -->
                        <input type="text" id="poiTitle"/>
                        <input type="text" id="poiDescription"/>
                        <input type="text" id="poiLon"/>
                        <input type="text" id="poiLan"/>
                        <button type="button" onclick="sendData()">Submit</button>
                    </form>
                    <div id="result" style="display: flex; flex-wrap: wrap">
                        <tags:card id="clone-id" deleteLink="#" description="Testing" title="Testing" display="none"/>
                    </div>
                    <script>
                        function sendData() {
                            let poiTitle = document.getElementById("poiTitle").value;
                            let poiDescription = document.getElementById("poiDescription").value;
                            let poiLon = document.getElementById("poiLon").value;
                            let poiLan = document.getElementById("poiLan").value;
                            //let cloned = document.getElementById("clone-id-card").cloneNode(true);

                            let result = document.getElementById("result");
                            jQuery.ajax({
                                type: "POST",
                                url: "testingAjaxServlet", // Servlet URL
                                data: { data: inputData },
                                success: function(response) {
                                    // Create a new div element
                                    let card = document.createElement("div");

                                    // Set attributes for the div
                                    card.id = "id-card";
                                    card.style.width = "20%";
                                    card.style.height = "350px";

                                    // Create an image element
                                    let imgElement = document.createElement("img");
                                    imgElement.style.height = "50%";
                                    imgElement.className = "card-img-top";
                                    imgElement.src = "images/beispiel_berge.jpg";
                                    imgElement.alt = "don't care";

                                    // Create a title element
                                    let titleElement = document.createElement("h5");
                                    titleElement.className = "card-title";
                                    titleElement.textContent = poiTitle;

                                    // Create a paragraph element
                                    let paragraphElement = document.createElement("p");
                                    paragraphElement.className = "card-text";
                                    paragraphElement.textContent = poiDescription + " " + poiLon + " " + poiLan;

                                    // Create a link element (a) for the button
                                    let linkElement = document.createElement("a");
                                    linkElement.href = "#";
                                    linkElement.className = "btn btn-primary";
                                    linkElement.style.margin = "20px";
                                    linkElement.textContent = "Delete";

                                    // Append the child elements to the idCardDiv
                                    card.appendChild(imgElement);
                                    card.appendChild(titleElement);
                                    card.appendChild(paragraphElement);
                                    card.appendChild(linkElement);

                                    result.appendChild(card);
                                },
                                error: function () {
                                    //Handle error
                                }
                            });
                        }
                    </script>
                </div>

                <!-- Rezensionen -->
                <button class="btn btn-light" onclick="toggleContent('rezensionen')">Rezensionen</button>
                <div id="rezensionen-content" class="content">
                    <!-- TODO Rezensionen generieren -->
                    <p>Hier sind Rezensionen zum Hike.</p>
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