<%@ tag pageEncoding="utf-8" %>
<%@ attribute name="id"%>
<%@ attribute name="title"%>
<%@ attribute name="description"%>
<%@ attribute name="lon"%>
<%@ attribute name="lat"%>
<%@ attribute name="src"%>
<%@ attribute name="hikeId"%>
<%@ attribute name="poiId"%>

<div id="<%=id%>-POICard" class="card bg-light" style="width: 24.25%; margin-top: 20px">
    <img class="card-img-top" src="data:image/png;base64,<%=src%>" alt="" style="max-height: 150px; object-fit: cover">
    <div class="d-flex flex-column" style="margin: 3%; height: 100%">
        <h5 class="card-title"><%=title%></h5>
        <p class="card-text" style="font-size: 12px;"><%=description%> Lat: <%=lat%>, Lon: <%=lon%></p>
        <button name="deletePOIButton" data-poi-id="<%=poiId%>" class="btn btn-danger" style="width: 90%; align-self: center; margin-top: auto;">Delete</button>
    </div>
</div>