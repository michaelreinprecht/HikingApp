package servlets.POIServlets;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Hike;
import models.PointOfInterest;
import database.Database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/deletePOIServlet")
@MultipartConfig
public class DeletePOIServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";
        String poiId = request.getParameter("poiId");
        try {
            //Get the POI based on its id, as well as the hike related to the POI.
            PointOfInterest poi = Database.getPointOfInterestById(poiId);
            Hike hike = Database.getHikeById(poi.getPointOfInterestHike().getHikeId());
            String hikeId = hike.getHikeId();

            //If users does not own the hike or is an admin, redirect to detail page and display error.
            HttpSession session = request.getSession();
            boolean loggedIn = request.getSession().getAttribute("username") != null;
            boolean ownsHike = loggedIn && (hike.getHikeOfUser() != null) && hike.getHikeOfUser().getUserName().equals(session.getAttribute("username"));
            boolean isAdmin = session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin");
            if (!ownsHike && !isAdmin) {
                error = "You are not authorized to delete Points of Interest for this hike.";
                response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&error=" + response.encodeURL(error));
                return;
            }

            //Remove the POI from the hikes list of POIs and update the hike in the database.
            List<PointOfInterest> pointsOfInterest = hike.getHikePointsOfInterest();
            pointsOfInterest.remove(poi);
            hike.setHikePointsOfInterest(pointsOfInterest);
            Database.update(hike);

            //Delete the POI from the database.
            Database.delete(poi);
            response.getWriter().write("deleted");
        }
        catch (IOException | SQLException e) {
            response.getWriter().write(e.getMessage());
        }
    }
}