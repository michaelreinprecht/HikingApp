<%--
  Created by IntelliJ IDEA.
  User: kilic
  Date: 13.12.2023
  Time: 09:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>registration</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

    <link rel="stylesheet" type="text/css" href="css/discover.css">

    <link rel="stylesheet" type="text/css" href="css/login.css">

    <link rel="stylesheet" type="text/css" href="css/registration.css">

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


<div class="bg-image p-5 text-center shadow-1-strong text-white flex-column align-items-center" style="background-image: url(images/registrationBackground.jpg);
     background-size: cover;
     background-position: center center;
     height: 100vh;">
    <% if (request.getAttribute("error") != null) { %>
    <p style="color: #ffffff; font-size: 20px; text-shadow: 2px 2px 4px #000000"><%= request.getAttribute("error") %></p>
    <% } %>

    <div class="d-flex justify-content-center align-items-center h-100">
        <div class="cardReg p-5 rounded shadow-lg" style="width: 500px;height: 70%"> <!-- White area with shadow and rounded corners -->
            <form method="POST" action="RegistrationServlet">
                <div class="form-group">
                    <h2 class="mb-4" style="color: #ffffff; text-shadow: 2px 2px 4px #000000;">Create Account</h2>
                  </div>
                <div class="form-group">
                    <input type="text" class ="form-control" name="username" aria-label="Username"
                           placeholder="Username" style="background-color: rgba(255, 255, 255, 0.8)">
                </div>

                <div class="form-group">
                    <input type="password" class="form-control" name="password" aria-label="Password"
                           placeholder="Password" style="background-color: rgba(255, 255, 255, 0.8)">
                </div>

                <div class="form-group">
                    <input type="password" class="form-control" name="password2" aria-label="Password2"
                           placeholder="Enter password again" style="background-color: rgba(255, 255, 255, 0.8)">
                </div>

                <button type="submit" class="btn btn-primary" style="background-color:rgba(13, 182, 15, 0.8); border-color: #07773a;">
                        Create Account!
                </button>
            </form>
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
