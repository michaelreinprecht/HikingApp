<%@ tag pageEncoding="utf-8" %>
<%@ attribute name="id"%>
<%@ attribute name="title"%>
<%@ attribute name="display"%>
<%@ attribute name="description"%>
<%@ attribute name="deleteLink"%>
<div id="<%=id%>-card" class="" style="width: 20%; height: 350px; display:<%=display%>;">
    <img style="height: 50%" class="card-img-top" src="images/beispiel_berge.jpg" alt="don't care">
    <h5 id="xytitle" class="card-title"><%=title%></h5>
    <p class="card-text"><%=description%></p>
    <a href="<%=deleteLink%>" class="btn btn-primary" style="margin: 20px">Delete</a>
</div>