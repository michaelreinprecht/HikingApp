package servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Hike;
import models.PointOfInterest;
import myHikeJava.Database;

@WebServlet("/deletePOIServlet")
@MultipartConfig
public class DeletePOIServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String poiId = request.getParameter("poiId");
        try {
            //Get the POI based on its id, as well as the hike related to the POI.
            PointOfInterest poi = Database.getPointOfInterestById(poiId);
            Hike hike = Database.getHikeById(poi.getPointOfInterestHike().getHikeId());

            //Remove the POI from the hikes list of POIs and update the hike in the database.
            List<PointOfInterest> pointsOfInterest = hike.getHikePointsOfInterest();
            pointsOfInterest.remove(poi);
            hike.setHikePointsOfInterest(pointsOfInterest);
            Database.update(hike);

            //Delete the POI from the database.
            Database.delete(poi);
            response.getWriter().write("deleted");
        }
        catch (IOException e) {
            response.getWriter().write(e.getMessage());
        }
    }
}