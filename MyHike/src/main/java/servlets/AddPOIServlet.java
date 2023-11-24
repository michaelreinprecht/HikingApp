package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Retrieve data from the Ajax request
        // Retrieve text data
        //Get values from parameters
        String poiId = UUID.randomUUID().toString(); //Create random UUID for POI
        String poiTitle = request.getParameter("poiTitle");
        String poiDescription = request.getParameter("poiDescription");
        String poiLon = request.getParameter("poiLon");
        String poiLat = request.getParameter("poiLat");
        String hikeId = request.getParameter("hikeId");

        // Retrieve file data
        Part filePart = request.getPart("poiImage");


        String error = "";
        try {
            //Create new hike object based on the data entered in create.jsp
            Hike hike = Database.getHikeById(hikeId);
            PointOfInterest poi = new PointOfInterest(poiId, poiTitle, poiDescription, new BigDecimal(poiLon), new BigDecimal(poiLat), getBase64(filePart), hike);

            Database.insert(poi);

            List<PointOfInterest> pointsOfInterest = hike.getHikePointsOfInterest();
            pointsOfInterest.add(poi);
            hike.setHikePointsOfInterest(pointsOfInterest);

            //Insert hike into database
            Database.update(hike);

        } catch (IOException e) {
            error = e.getMessage();
        }
        if (!error.isEmpty()) {
            //response.sendRedirect("edit.jsp?Id=" + request.getParameter("Id") + "&error=" + response.encodeURL(error));
        } else {
            //response.sendRedirect("index.jsp?editSuccess=true");
        }



        response.getWriter().write(poiId);
    }
}