<%@ tag import="java.util.Objects" %>
<%@ tag pageEncoding="utf-8" %>
<%@ attribute name="alert" required="true"%>


    <%
        if (Objects.equals(alert, "createSuccess")) {
    %>
    <div class="alert alert-success" role="alert" style="margin-top: 10px; margin-bottom: 10px">
        Successfully created your new hike - you should now be able to see it in the discovery tab.
    </div>
    <%
        }
        else if (Objects.equals(alert, "editSuccess")) {
    %>
    <div class="alert alert-success" role="alert" style="margin-top: 10px; margin-bottom: 10px">
        Successfully edited your hike - you can find it and view your changes in the discovery tab.
    </div>
    <%
        }
        else if (Objects.equals(alert, "deleteSuccess")) {
    %>
    <div class="alert alert-success" role="alert" style="margin-top: 10px; margin-bottom: 10px">
        Successfully deleted your hike.
    </div>
    <%
        }
    %>
