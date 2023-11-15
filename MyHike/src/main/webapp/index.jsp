<%@ page import="myHikeJava.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Hike</title>

    <!-- Bootstrap link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Font Awesome Icons link -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

    <!-- Link to css files -->
    <link rel="stylesheet" href="css/global.css">
</head>
<body>
<!-- Navigation bar -->
<nav class="navbar sticky-top navbar-expand-lg navbar-dark">
    <a class="navbar-brand" href="#">
        <img src="images/icon3.png"alt ="MyHike" class="icon">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02"
            aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item active">
                <a class="nav-link" href="discover.jsp">Discover <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="create.jsp">Create Hike</a>
            </li>
        </ul>
    </div>
</nav>


 <div class="container-fluid" style="background-color: white; padding: 0">
    <div class="row" style="width: 100%; margin: 0; padding: 0">
        <div class="col-md-auto" style="margin-left: 10px;">
            <!-- Description of the Hike, HINT: create multiple div elements with class="row" instead of only using <br/> -->
            Welcome to the hiking application!<br/>
            <!-- Assume Database.getAllHikes() returns a list of hikes with their IDs and names -->
            <%
                // Assuming Database.getAllHikes() returns a list of Hike objects with id and name properties
                List<Hike> hikes = Database.getAllHikes();
                for (Hike hike : hikes) {
                    String image = hike.getHikeImage();
            %>
            <div>
                <!-- Return a link to the detail page of every hike together with an Image -->
                <a href="detail.jsp?Id=<%=hike.getHikeId()%>"><%=hike.getHikeName()%></a>
                <img alt="<%=hike.getHikeName()%>" src="data:image/png;base64,<%=image%>" width="150">
            </div>
            <% } %>
        </div>
        <div class="col-md" style="background-color: lightblue; padding: 0;">
            <img src="images/map.png" style="width: 100%; position: fixed" alt="Map Placeholder"/>
        </div>
    </div>
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