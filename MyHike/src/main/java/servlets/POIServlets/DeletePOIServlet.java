package servlets.POIServlets;

import database.Database;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Hike;
import models.PointOfInterest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/deletePOIServlet")
@MultipartConfig
public class DeletePOIServlet extends POIServletUtils {
    private String error;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        deletePOI(request, response);
    }

    //Attempts to delete the POI. If this method fails the error will be handled in the ajax error segment in detail.js
    protected void deletePOI(HttpServletRequest request, HttpServletResponse response) throws IOException {
        error = "";
        String poiId = request.getParameter("poiId");
        try {
            //Get the POI based on its id, as well as the hike related to the POI.
            PointOfInterest poi = Database.getPointOfInterestById(poiId);
            Hike hike = Database.getHikeById(poi.getPointOfInterestHike().getHikeId());

            if (!handleAuthForHike(hike, request, response)) {
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
            error = e.getMessage();
            response.getWriter().write(error);
        }
    }

    public String getError() {
        return error;
    }
}