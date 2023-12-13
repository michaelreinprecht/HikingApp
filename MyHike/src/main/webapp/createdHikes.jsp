<%@ page import="models.Hike" %>
<%@ page import="java.util.List" %>
<%@ page import="database.Database" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>

    <link rel="stylesheet" type="text/css" href="css/discover.css">

    <link rel="stylesheet" type="text/css" href="css/createdHikes.css">
</head>

<!-- redirect to login page if no user is logged in -->
<%
    if ((request.getSession(false).getAttribute("username") == null)){
%>
<jsp:forward page="login.jsp"/>
<%
    }
%>

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
            <li class="nav-item active">
                <a class="nav-link" href="createdHikes.jsp">Your Hikes</a>
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

<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <!-- Display successAlert based on successAlert parameter or error. -->
        <%
            String successAlert = request.getParameter("successAlert");
            String error = request.getParameter("error");
        %>
        <tags:multiAlert alert='<%=successAlert%>' error="<%=error%>"/>
        <h1 class="display-8">Your hikes</h1>
        <div class="row gutter">
            <%
                List<Hike> hikes = Database.getHikesByUser((String) session.getAttribute("username"));
                for (Hike hike : hikes){
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
                }
            %>
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
