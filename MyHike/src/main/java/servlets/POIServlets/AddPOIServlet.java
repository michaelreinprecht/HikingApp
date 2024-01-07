package servlets.POIServlets;

import database.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Hike;
import models.PointOfInterest;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet("/addPOIServlet")
@MultipartConfig
public class AddPOIServlet extends POIServletUtils {
    private String error;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        addPOI(request, response);
    }

    //Adds a new point of interest to the hike passed in the request using the values passed in the request.
    //If this method fails errors are handled in the ajax segment in detail.js.
    protected void addPOI(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //If an error it will be saved to this parameter and later be displayed
        error = "";

        //Get values from parameters
        String poiId = UUID.randomUUID().toString(); //Create random UUID for POI
        String poiTitle = request.getParameter("poiTitle");
        String poiDescription = request.getParameter("poiDescription");
        String poiLon = request.getParameter("poiLon");
        String poiLat = request.getParameter("poiLat");
        String hikeId = request.getParameter("hikeId");

        try {
            //Get Part of image
            Part filePart = request.getPart("poiImage");

            //Create new hike object based on the data entered in create.jsp
            Hike hike = Database.getHikeById(hikeId);
            PointOfInterest poi = new PointOfInterest(poiId, poiTitle, poiDescription, new BigDecimal(poiLon), new BigDecimal(poiLat), getBase64(filePart), hike);

            if (!handleAuthForHike(hike, request, response)) {
                return;
            }

            //Insert the new Point of Interest
            Database.insert(poi);

            //Update the current hike with a new List of POIs, including our new POI
            List<PointOfInterest> pointsOfInterest = hike.getHikePointsOfInterest();
            pointsOfInterest.add(poi);
            hike.setHikePointsOfInterest(pointsOfInterest);
            Database.update(hike);

        } catch (IOException | ServletException | SQLException e) {
            String errorMessage = "An error occurred while editing the hike.Please try again later.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
        if (!error.isEmpty()) {
            response.getWriter().write(error);
        } else {
            response.getWriter().write(poiId);
        }
    }

    public String getError() {
        return error;
    }
}