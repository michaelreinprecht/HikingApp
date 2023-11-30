<%--
  Created by IntelliJ IDEA.
  User: kilic
  Date: 14.11.2023
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ page import="myHikeJava.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="models.Month" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="models.Region" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.sql.Time" %>
<%@ page import="java.math.BigDecimal" %>



<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <title>hikes</title>

  <!-- Bootstrap link -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

  <!-- Font Awesome Icons link -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

  <!-- Link to hikelist.css -->
  <link rel="stylesheet" href="css/hikelist.css">


</head>
<body>

<!-- Navigation bar -->
<nav class="navbar sticky-top navbar-expand-lg navbar-dark" style="background-color: #07773a; height: 80px">
  <a class="navbar-brand" href="index.jsp">
    <img src="images/icon3.png" alt="MyHike" style=" width: 90px; height: 70px; margin-bottom: 5px">
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
  </div>
</nav>


<!-- Searchbar -->
<form method="post">
    <div class="input-group mb-3 mx-auto" id="hikelist-searchbar">
        <input type="text" class="form-control" name="searchQuery" aria-label="Amount (to the nearest dollar)"
               placeholder="Search by name or region!">
        <span class="input-group-text">
            <button type="submit" class="searchButton">Search</button>
        </span>
    </div>
</form>


<form method="post" action="hikelist.jsp">
    <div class="row">
        <div class="col-md-2 mb-3">
            <label class="input-group-text" for="durationFilter">Max. Duration (in hours):</label>
            <div class="input-group">
                <input class="form-control" name="durationFilter" id="durationFilter" type="time" required>
            </div>
        </div>

        <div class="col-md-2 mb-3">
            <label class="input-group-text" for="distanceFilter">Max. Distance (in km):</label>
            <input class="form-control" type="number" name="distanceFilter" id="distanceFilter" placeholder="No Filter">
        </div>

        <div class="col-md-2 mb-3">
            <label class="input-group-text" for="staminaFilter">Level of Fitness (1-5):</label>
            <input class="form-control" type="number" name="staminaFilter" id="staminaFilter" min="1" max="5" placeholder="No Filter">
        </div>

        <div class="col-md-2 mb-3">
            <label class="input-group-text" for="OverallDifficultyFilter">Overall Difficulty (1-5):</label>
            <input class="form-control" type="number" name="OverallDifficultyFilter" id="OverallDifficultyFilter" min="1" max="5" placeholder="No Filter">
        </div>

        <div class="col-md-2 mb-3">
            <label class="input-group-text" for="landscapeFilter">Landscape (1-5):</label>
            <input class="form-control" type="number" name="landscapeFilter" id="landscapeFilter" min="1" max="5" placeholder="No Filter">
        </div>

        <div class="col-md-2 mb-3">
            <label class="input-group-text" for="strengthFilter">Min. Strength (1-5):</label>
            <input class="form-control" type="number" name="strengthFilter" id="strengthFilter" min="1" max="5" placeholder="No Filter">
        </div>

        <div class="col-md-2 mb-3">
            <label class="input-group-text" for="monateFilter">Select Months</label>
            <select class="form-control" id="monateFilter" name="monateFilter">
                   <%
                    String selectedMonthsBitmap = "";
                    String[] selectedMonths = models.Month.getMonthsByBitmap(selectedMonthsBitmap);

                  for(String month: models.Month.ALL_MONTHS) { %>
                <option value="<%=month%>" <% if (Arrays.asList(selectedMonths).contains(month)) { %>selected<% } %>>
                    <%=month%>
                </option>
                <% } %>
            </select>
        </div>
    </div>

    <div class="row mt-3">
        <div class="col-md-6 offset-md-6 text-right">
            <button type="submit" class="btn btn-success">Apply Filters</button>
        </div>
    </div>
</form>





<div class="container">
    <!-- Header -->
    <div class="title">
        <h3 class="text-center">
            These Hikes are based on your search!
        </h3>
    </div>
    <div id="hikes">
    <%
        List<Hike> hikes = Database.getAllHikes();
        boolean noMatchingHikesFound = false;

        String searchQuery = request.getParameter("searchQuery"); // Holen der Suchanfrage aus der Suchzeile
        if (searchQuery != null && !searchQuery.isEmpty()) {
            hikes = hikes.stream()
                    .filter(hike ->
                            hike.getHikeRegion().getRegionName().toLowerCase().contains(searchQuery.toLowerCase()) ||   //Sucht Region
                            hike.getHikeName().toLowerCase().contains(searchQuery.toLowerCase()))   //Sucht Name
                    .collect(Collectors.toList()); //Gibt dann die Liste mit den Hikes, die das Suchbegriff im Name oder Region haben


        }
        List<Hike> filteredHikes = (List<Hike>) request.getAttribute("filteredHikes");

        // TODO
        String durationFilter = request.getParameter("durationFilter");
        String distanceFilter = request.getParameter("distanceFilter");
        String staminaFilter = request.getParameter("staminaFilter");
        String strengthFilter = request.getParameter("strengthFilter");
        String landscapeFilter = request.getParameter("landscapeFilter");
        String monateFilter = request.getParameter("monateFilter");
        String OverallDifficultyFilter = request.getParameter("OverallDifficultyFilter");

        //Apply filters
        if (durationFilter != null && !durationFilter.isEmpty()) {
            try {
                String formattedDuration = durationFilter + ":00";
                Time duration = Time.valueOf(formattedDuration);

                hikes = hikes.stream()
                        .filter(hike ->
                                hike.getHikeDuration() != null &&
                                        hike.getHikeDuration().toLocalTime().compareTo(duration.toLocalTime()) <= 0
                        )
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                System.err.println("Fehler: durationFilter ist kein gültiges Zeitformat");
            }
        }

        if (distanceFilter != null && !distanceFilter.isEmpty()) {
            try {
                BigDecimal distance = new BigDecimal(distanceFilter);
                BigDecimal upperBound = distance.add(new BigDecimal("10"));

                hikes = hikes.stream()
                        .filter(hike -> {
                            BigDecimal hikeDistance = hike.getHikeDistance();
                            return hikeDistance != null &&
                                    hikeDistance.compareTo(distance) >= 0 &&
                                    hikeDistance.compareTo(upperBound) <= 0;
                        })
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.err.println("Fehler: distanceFilter ist keine gültige Zahl");
            }
        }


        if (staminaFilter != null && !staminaFilter.isEmpty() && !staminaFilter.equals("null")) {
            int stamina = Integer.parseInt(staminaFilter);
            hikes = hikes.stream()
                    .filter(hike -> hike.getHikeStamina() == stamina)
                    .collect(Collectors.toList());
        }

        if (strengthFilter != null && !strengthFilter.isEmpty() && !strengthFilter.equals("null")) {
            int strength = Integer.parseInt(strengthFilter);
            hikes = hikes.stream()
                    .filter(hike -> hike.getHikeStrength() == strength)
                    .collect(Collectors.toList());
        }

        if (landscapeFilter != null && !landscapeFilter.isEmpty() && !landscapeFilter.equals("null")) {
            try {
                int landscape = Integer.parseInt(landscapeFilter);
                hikes = hikes.stream()
                        .filter(hike -> hike.getHikeLandscape() == landscape)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.err.println("Fehler: landscapeFilter ist keine gültige Zahl");
            }
        }

        if (OverallDifficultyFilter != null && !OverallDifficultyFilter.isEmpty() && !OverallDifficultyFilter.equals("null")) {
            try {
                int difficulty = Integer.parseInt(OverallDifficultyFilter);
                hikes = hikes.stream()
                        .filter(hike -> hike.getHikeDifficulty() == difficulty)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.err.println("Fehler: overallDifficultyFilter ist keine gültige Zahl");
            }
        }



        // TODO

        if (filteredHikes != null && !filteredHikes.isEmpty()){
            hikes = filteredHikes;
        }

        if (hikes.isEmpty()){
            noMatchingHikesFound = true;
        }

        if(hikes == null || hikes.isEmpty()){
    %>
    </div>
    <div class="alert alert-warning" role="alert">
        Unfortunately, there are no matching hikes with your search :(     <!-- Fehlermeldung, falls keine Hikes zutreffen-->
    </div>
    <%
        } else {
            for (Hike hike : hikes) {
            String image = hike.getHikeImage();
    %>
    <div class="row">
        <div class="col-md-6">
            <!-- Bild und Name -->
            <img class="imageBoxHikeList" id="uploadedImageHikeList" alt="<%=hike.getHikeName()%>" src="data:image/png;base64,<%=image%>">
            <br>
            <a class="hikeTitle" href="detail.jsp?Id=<%=hike.getHikeId()%>"><%=hike.getHikeName()%></a>
        </div>
        <div class="col-md-6">
            <!-- Daten der Wanderung -->
            <div class="row">
                <div class="col-md-4">
                    <!-- Dauer -->
                    <div class="group">
                        <img src="images/uhr_dauer.png" alt="uhr" class="icons">
                        <h5 class="text-center">
                            <% if (hike.getHikeDuration() != null) {
                                LocalTime localTime = hike.getHikeDuration().toLocalTime();
                                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
                                String formattedTime = localTime.format(outputFormatter);
                            %>
                            <%= formattedTime %> hours
                            <% } else { %>
                            ? hours
                            <% } %>
                        </h5>
                    </div>
                </div>

                <div class="col-md-4">
                    <!-- Distance -->

                    <div class="group">
                        <img src="images/streckenlänge.png" alt="streckenlänge" class="icons">
                        <h5 class="text-center">
                            <% if (hike.getHikeDistance() != null) { %>
                            <%= hike.getHikeDistance() %>km
                            <% } else { %>
                            ?km
                            <% } %>
                        </h5>
                    </div>
                </div>

                <div class="col-md-4">
                    <!-- Altitude -->
                    <div class="group">
                        <img src="images/altitude_icon.png" alt="altitude" class="icons">
                        <h5 class="text-center">
                            <% if (hike.getHikeAltitude() != null) { %>
                            <%= hike.getHikeAltitude() %>m
                            <% } else { %>
                            ?m
                            <% } %>
                        </h5>
                    </div>
                </div>

                <div class="col-md-4">
                    <!-- Overall Difficulty Rating -->
                    <div class="rating-label"><b>Overall Difficulty:</b></div>
                    <%
                        int i;
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
                <!-- Beschreibung -->
                <p><%= hike.getHikeDescription() %></p>
            </div>
        </div>
    </div>
    <% } %>
    <!-- Trennlinie -->
    <hr size="8" color="green">
    <% } %>
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
