<%@ page import="java.util.Arrays" %>
<%@ page import="myHikeJava.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: kenan
  Date: 14.11.2023
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Discover</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Discover</title>

  <!-- Bootstrap link -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

  <!-- Font Awesome Icons link -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

  <!-- Link to detail.css -->
  <link rel="stylesheet" href="css/discover.css">

  <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
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

<div class="bg-image p-5 text-center shadow-1-strong rounded text-white" id="233" style="background-image: url('https://mdbcdn.b-cdn.net/img/new/slides/003.webp');">
  <h1 class="mb-3 h2">Discover a whole new adventure</h1>

  <!-- Remove this shit, just for testing -->
  <form id="myForm">
    <!-- Your input fields here -->
    <input type="text" id="dataInput" />
    <button type="button" onclick="sendData()">Submit</button>
  </form>
  <div id="result"></div>
  <script>
    function sendData() {
      let inputData = document.getElementById("dataInput").value;

      $.ajax({
        type: "POST",
        url: "testingAjaxServlet", // Servlet URL
        data: { data: inputData },
        success: function(response) {
          $("#result").html(inputData);
        },
        error: function () {
          //Handle error
        }
      });
    }
  </script>

  <form method="POST" action="hikelist.jsp">
    <div class="input-group mb-3 mx-auto" id="discover-searchbar">
      <input type="text" class="form-control" name="searchQuery" aria-label="Amount (to the nearest dollar)"
           placeholder="Search by Region!">

      <span class="input-group-text">
        <button type="submit" class="searchButton">Search</button>
      </span>
    </div>
  </form>

  <p> This is a test</p>

</div>

<div>

  </div>

<div class="jumbotron jumbotron-fluid">
  <div class="container">
    <h1 class="display-8">Popular this Season</h1>
    <!--
    Sample card

    <div class="bg-image card shadow-1-strong" style="background-image: url('https://mdbcdn.b-cdn.net/img/new/slides/003.webp');">
      <div class="card-body text-white">
        <h5 class="card-title">Card title</h5>
        <p class="card-text">
          Some quick example text to build on the card title and make up the bulk of the
          card's content.
        </p>
        <a href="#!" class="btn btn-outline-light">Button</a>
      </div>
    </div>
    -->
    <div class="row gutter">
      <%
        int i = 0;
        List<Hike> hikes = Database.getAllHikes();
        while (i != 3) {
          Hike h1 = hikes.get(i);
          String image = h1.getHikeImage();
      %>
      <div class="col-sm-4">

        <div class="bg-image card shadow-1-strong" style="background-image: url('data:image/png;base64,<%=image%>'); background-size: cover; ">
          <div class="card-body text-white" style="position: absolute; bottom: 0; left: 0; right: 0; background-color: rgba(0, 0, 1, 0.7); height: 50%;">
            <div class="card-body">
              <h5 class="card-title"><%= h1.getHikeName()%></h5>
              <p class="card-text">
                <small class="text-muted">
                  Strength: <%= h1.getHikeStrength()%>
                  Stamina: <%= h1.getHikeStamina()%>
                  Difficulty: <%= h1.getHikeDifficulty()%>
                </small>
              </p>
            </div>
          </div>
        </div>
      </div>
      <%
          i += 1;
        }
      %>
    </div>
    <h1 class="display-8">Popular near you</h1>
    <div class="row gutter">
      <%
        while (i != 6) {
          Hike h1 = hikes.get(i);
          String image = h1.getHikeImage();
      %>
      <div class="col-sm-4">

        <div class="bg-image card shadow-1-strong" style="background-image: url('data:image/png;base64,<%=image%>'); background-size: cover;">
          <div class="card-body text-white" style="position: absolute; bottom: 0; left: 0; right: 0; background-color: rgba(0, 0, 1, 0.7); height: 50%;">
            <div class="card-body">
              <h5 class="card-title"><%= h1.getHikeName()%></h5>
              <p class="card-text">
                <small class="text-muted">
                  Strength: <%= h1.getHikeStrength()%>
                  Stamina: <%= h1.getHikeStamina()%>
                  Difficulty: <%= h1.getHikeDifficulty()%>
                </small>
              </p>
            </div>
          </div>
        </div>
      </div>
      <%
          i+= 1;
        }
      %>
    </div>

  </div>

</div>
</body>
</html>
