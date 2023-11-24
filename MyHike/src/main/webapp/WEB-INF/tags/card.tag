<%@ tag pageEncoding="utf-8" %>
<%@ attribute name="id"%>
<%@ attribute name="title"%>
<%@ attribute name="description"%>
<%@ attribute name="lon"%>
<%@ attribute name="lat"%>
<%@ attribute name="src"%>
<%@ attribute name="hikeId"%>
<%@ attribute name="poiId"%>

<div id="<%=id%>-POICard" class="card" style="width: 45%; margin: 2.5%;">
    <img class="card-img-top" src="data:image/png;base64,<%=src%>" alt="">
    <div class="d-flex flex-column" style="margin: 3%; height: 100%">
        <h5 class="card-title"><%=title%></h5>
        <p class="card-text"><%=description%> Lat: <%=lat%>, Lon: <%=lon%></p>
        <button name="deletePOIButton" data-poi-id="<%=poiId%>" class="btn btn-danger" style="width: 90%; align-self: end; margin-top: auto;">Delete</button>
    </div>
</div>