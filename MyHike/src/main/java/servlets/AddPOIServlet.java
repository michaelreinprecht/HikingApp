package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Hike;
import models.PointOfInterest;
import myHikeJava.Database;
import myHikeJava.ServletUtils;

@WebServlet("/addPOIServlet")
@MultipartConfig
public class AddPOIServlet extends ServletUtils {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Get values from parameters
        String poiId = UUID.randomUUID().toString(); //Create random UUID for POI
        String poiTitle = request.getParameter("poiTitle");
        String poiDescription = request.getParameter("poiDescription");
        String poiLon = request.getParameter("poiLon");
        String poiLat = request.getParameter("poiLat");
        String hikeId = request.getParameter("hikeId");

        //If an error it will be saved to this parameter and later be displayed
        String error = "";
        try {
            //Get Part of image
            Part filePart = request.getPart("poiImage");

            //Create new hike object based on the data entered in create.jsp
            Hike hike = Database.getHikeById(hikeId);
            PointOfInterest poi = new PointOfInterest(poiId, poiTitle, poiDescription, new BigDecimal(poiLon), new BigDecimal(poiLat), getBase64(filePart), hike);

            //Insert the new Point of Interest
            Database.insert(poi);

            //Update the current hike with a new List of POIs, including our new POI
            List<PointOfInterest> pointsOfInterest = hike.getHikePointsOfInterest();
            pointsOfInterest.add(poi);
            hike.setHikePointsOfInterest(pointsOfInterest);
            Database.update(hike);

        } catch (IOException | ServletException e) {
            error = e.getMessage();
        }
        if (!error.isEmpty()) {
            response.getWriter().write(poiId);
        } else {
            response.getWriter().write(error);
        }
    }
}