<%@ page import="com.example.myhike.Database" %><%--
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

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="Search">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                </form>
            </div>
        </nav>

        <div class="container-fluid" style="background-color: white; padding: 0">
            <div class="row" style="width: 100%; margin: 0; padding: 0">
                <div class="col-md-auto" style="margin-left: 10px;">
                    <%
                            String id = request.getParameter("Id");
                            var Hike=Database.getHikeById(Integer.parseInt(id));%><br>
                    <label style="text-align: center; color: green; font-style: italic">Hike anzeigen</label><br>
                    <label>Name: </label><br>
                    <label><%=Hike.getHikeName()%></label><br>
                    <label>Description:</label><br>
                    <label><%=Hike.getHikeDescription()%></label><br>
                    <label>Region: <%=Hike.getHikeRegion().getRegionName()%></label><br>
                    <label>GPS: <%=Hike.getHikeStartLon()%> bis </label> <label><%=Hike.getHikeEndLon()%></label><br>
                    <label>Altitude: <%=Hike.getHikeAltitude()%>m </label>
                    <label>Distance: <%=Hike.getHikeDistance()%>km</label><br>
                    <label>Duration: <%=Hike.getHikeDuration()%></label>
                    <label>Landscape: <%=Hike.getHikeLandscape()%></label>
                </div>
                <div class="col-md" style="background-color: lightblue; padding: 0;">
                    <img src="images/map.png" style="width: 100%; position: fixed" alt="Map Placeholder"/>
                </div>
            </div>
        </div>
    </body>
</html>
