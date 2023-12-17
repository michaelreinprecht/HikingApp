<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

    <link rel="stylesheet" type="text/css" href="css/discover.css">

    <link rel="stylesheet" type="text/css" href="css/login.css">
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
        <ul class="navbar-nav">
            <li class="nav-item">
                <%if (session.getAttribute("username") == null) { %>
                <a class="nav-link" href="login.jsp">Login</a>
                <% } else { %>
                <a class="nav-link" href="logoutServlet"><%=session.getAttribute("username")%><br>Logout</a>
                <% } %>
            </li>
        </ul>
    </div>
</nav>

<div class="bg-image p-5 text-center shadow-1-strong text-white flex-column align-items-center"
     style="background-image: url(images/beispiel_berge.jpg);
     background-size: cover;
     background-position: center center;
     height: 60%">
    <%-- Display error if available by login--%>
    <% if (request.getAttribute("error") != null) { %>
    <p style="color: white; font-size: 25px"><%= request.getAttribute("error") %></p>
    <% } %>

    <%-- Other content of your JSP page goes here --%>
    <h1 class="mb-3 h2" style="margin-top: 100px;color: #fff; /* Weißer Text */
        text-shadow: 2px 2px 4px #000000;">Login to your Account</h1>

    <form method="POST" action="loginServlet">
        <div class="input-group mb-3 mx-auto" style="width: 500px;">
            <input type="text" class="form-control" name="username" aria-label="Username"
                   placeholder="Username" style="background-color: rgba(255, 255, 255, 0.8)">

            <input type="password" class="form-control" name="password" aria-label="Password"
                   placeholder="Password" style="background-color: rgba(255, 255, 255, 0.8)">

            <div class="input-group-append">
                <button type="submit" class="btn btn-primary" data-mdb-ripple-init
                        style="background-color: rgba(13, 182, 15, 0.8); border-color: #07773a; border-bottom-left-radius: 0; border-top-left-radius: 0">
                    Login <i class="fas fa-sign-in-alt"></i>
                </button>
            </div>

            <div class="registrationPart">
                <h2 class="mb-3 h2" style="color: #fff; /* Weißer Text */
                                    text-shadow: 2px 2px 4px #000000; /* Schwarzer Schatten für bessere Lesbarkeit */
                                    /*background-color: rgba(0, 0, 0, 0.5); /* Halbtransparenter Hintergrund */
                                    display: inline-block; /* Damit der Hintergrund nur so breit wie der Text ist */
                                    padding: 5px 10px; /* Etwas Abstand im Inneren des Hintergrunds */
                                    border-radius: 5px; /* Abgerundete Ecken für den Hintergrund */">
                    You do not have an account? Register here!</h2>

                <a href="registration.jsp" style="display: block">
                    <button type="button" class="btn btn-primary" data-mdb-ripple-init style="background-color: rgba(13, 182, 15, 0.8); border-color: #07773a; border-bottom-left-radius: 0; border-top-left-radius: 0">
                            Registration
                    </button>
                </a>

            </div>
        </div>
    </form>
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
