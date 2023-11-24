package servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Hike;
import models.PointOfInterest;
import myHikeJava.Database;

@WebServlet("/deletePOIServlet")
@MultipartConfig
public class DeletePOIServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String poiId = request.getParameter("poiId");

        PointOfInterest poi = Database.getPointOfInterestById(poiId);
        Hike hike = Database.getHikeById(poi.getPointOfInterestHike().getHikeId());
        List<PointOfInterest> pointsOfInterest = hike.getHikePointsOfInterest();
        pointsOfInterest.remove(poi);
        hike.setHikePointsOfInterest(pointsOfInterest);
        Database.update(hike);

        Database.delete(poi);
        response.getWriter().write("deleted");
    }
}