package servlets.POIServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Hike;
import servlets.ServletUtils;

import java.io.IOException;

//Contains basic methods useful for most POI related servlets.
public class POIServletUtils extends ServletUtils {
    //If users does not own the hike or is an admin, redirect to detail page and display error.
    protected boolean handleAuthForHike(Hike hike, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        boolean loggedIn = request.getSession().getAttribute("username") != null;
        boolean ownsHike = loggedIn && (hike.getHikeOfUser() != null) && hike.getHikeOfUser().getUserName().equals(session.getAttribute("username"));
        boolean isAdmin = session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin");
        if (!ownsHike && !isAdmin) {
            String error = "You are not authorized to create a Point of Interest for this hike.";
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hike.getHikeId()) + "&error=" + response.encodeURL(error));
            return false;
        }
        return true;
    }
}
