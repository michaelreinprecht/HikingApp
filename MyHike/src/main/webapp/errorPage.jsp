<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Error</title>

    <!-- Bootstrap link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Google font link -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Barlow&display=swap" rel="stylesheet">
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
            <li class="nav-item">
                <a class="nav-link" href="discover.jsp">Discover</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="create.jsp">Create Hike</a>
            </li>
            <%if (session.getAttribute("username") != null) { %>
            <li class="nav-item">
                <a class="nav-link" href="createdHikes.jsp">Your Hikes</a>
            </li>
            <% } %>
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
<%
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    Throwable throwable2 = (Throwable) request.getAttribute("javax.servlet.error.exception");

    if (statusCode != null) {
        request.setAttribute("errorCode", statusCode);
    } else if (throwable2 != null) {
        request.setAttribute("errorMessage", throwable2.getMessage());
    }

    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<h2>Error Details</h2>
<p>Error Type: <%= throwable2 %></p>
<p>Status Code: <%= statusCode %></p>
<p>Error Message: <%= errorMessage %></p>

<!-- You can display more details about the exception if needed -->

<!-- Bootstrap imports -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
