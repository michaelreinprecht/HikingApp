<%@ tag import="java.util.Objects" %>
<%@ tag pageEncoding="utf-8" %>
<%@ attribute name="alert" %>
<%@ attribute name="error" required="true" %>

<%
    if (error == null || error.isEmpty()) {
        if (alert != null && !alert.isEmpty()) {
%>
<div class="alert alert-success" role="alert" style="margin: 10px">
    <%=alert%>
</div>
<%
        }
    } else {
%>
<div class="alert alert-danger" role="alert" style="margin-top: 10px; margin-bottom: 10px">
    <%=error%>
</div>
<%
    }
%>
