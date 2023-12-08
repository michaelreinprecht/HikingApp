<%@ page import="database.Database" %>
<%@ page import="models.Hike" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
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

  <!-- Google font link -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Barlow&display=swap" rel="stylesheet">

  <!-- Link to css files -->
  <link rel="stylesheet" type="text/css" href="css/discover.css">
</head>
<body>
<!-- Navigation bar -->
<nav class="navbar sticky-top navbar-expand-lg navbar-dark" style="background-color: #07773a; height: 80px">
  <a class="navbar-brand" href="discover.jsp">
    <img src="images/icon3.png" alt="MyHike" style=" width: 90px; height: 70px; margin-bottom: 5px">
  </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02"
          aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
      <li class="nav-item active">
        <a class="nav-link" href="discover.jsp">Discover</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="create.jsp">Create Hike</a>
      </li>
    </ul>
  </div>
</nav>

<div class="bg-image p-5 text-center shadow-1-strong text-white flex-column align-items-center"
     style="background-image: url(images/nature.jpg);
     background-size: cover;
     background-position: center center;
     height: 60%;">
  <div style="background-color: rgba(0, 0, 0, 0.5); margin-left: 20%; margin-right: 20%; height: 100%; display: flex; flex-direction: column; justify-content: center; align-items: center;">
    <h1 class="mb-3 h2">Discover a whole new adventure</h1>

  <form method="POST" action="filterHikesServlet">
    <div class="input-group mb-3 mx-auto" style="width: 500px">
      <input type="text" class="form-control" name="searchQuery" aria-label="Amount (to the nearest dollar)"
             placeholder="Search a hike by name or region!" style="background-color: rgba(255, 255, 255, 0.8)">

        <button type="submit" class="btn btn-primary" data-mdb-ripple-init style="background-color: rgba(13, 182, 15, 0.8); border-color: #07773a; border-bottom-left-radius: 0; border-top-left-radius: 0">
          <i class="fas fa-search"></i>
        </button>
      </div>
    </form>

    <p>This is a test</p>
  </div>

</div>


<div class="jumbotron jumbotron-fluid">
  <div class="container">
    <!-- Display successAlert based on successAlert parameter. -->
    <%
      String successAlert = request.getParameter("successAlert");
    %>
    <tags:successAlert alert='<%=successAlert%>'/>
    <h1 class="display-8">Popular this Season</h1>
    <div class="row gutter">
      <%
        int i = 0;
        List<Hike> hikes = Database.getAllHikes();
        while (i != 3 && hikes.size() > i) {
          Hike hike = hikes.get(i);
          String image = hike.getHikeImage() != null ? hike.getHikeImage() : "";
      %>
      <div class="col-sm-4">
        <a href="detail.jsp?Id=<%=hike.getHikeId()%>">
        <div class="bg-image card shadow-1-strong" style="background-image: url('data:image/png;base64,<%=image%>'); background-size: cover;">
          <div class="card-body text-white" style="position: absolute; bottom: 0; left: 0; right: 0; background-color: rgba(0, 0, 1, 0.7); height: 50%;">
            <div class="card-body">
              <h5 class="card-title"><%= hike.getHikeName()%></h5>
              <p class="card-text">
                <small class="text-muted">
                  Strength: <%= hike.getHikeStrength()%>
                  Stamina: <%= hike.getHikeStamina()%>
                  Difficulty: <%= hike.getHikeDifficulty()%>
                </small>
              </p>
            </div>
          </div>
        </div>
        </a>
      </div>
      <%
          i += 1;
        }
      %>
    </div>
    <h1 class="display-8">Popular near you</h1>
    <div class="row gutter">
      <%
        while (i != 6  && hikes.size() > i) {
          Hike hike = hikes.get(i);
          String image = hike.getHikeImage() != null ? hike.getHikeImage() : "";
      %>
      <div class="col-sm-4">
        <a href="detail.jsp?Id=<%=hike.getHikeId()%>">
        <div class="bg-image card shadow-1-strong" style="background-image: url('data:image/png;base64,<%=image%>'); background-size: cover;">
          <div class="card-body text-white" style="position: absolute; bottom: 0; left: 0; right: 0; background-color: rgba(0, 0, 1, 0.7); height: 50%;">
            <div class="card-body">
              <h5 class="card-title"><%= hike.getHikeName()%></h5>
              <p class="card-text">
                <small class="text-muted">
                  Strength: <%= hike.getHikeStrength()%>
                  Stamina: <%= hike.getHikeStamina()%>
                  Difficulty: <%= hike.getHikeDifficulty()%>
                </small>
              </p>
            </div>
          </div>
        </div>
        </a>
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
