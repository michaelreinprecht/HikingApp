package servlets.POIServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.Hike;
import models.PointOfInterest;
import database.Database;
import servlets.ServletUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

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

            //If users does not own the hike or is an admin, redirect to detail page and display error.
            HttpSession session = request.getSession();
            boolean loggedIn = request.getSession().getAttribute("username") != null;
            boolean ownsHike = loggedIn && (hike.getHikeOfUser() != null) && hike.getHikeOfUser().getUserName().equals(session.getAttribute("username"));
            boolean isAdmin = session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin");
            if (!ownsHike && !isAdmin) {
                error = "You are not authorized to create a Point of Interest for this hike.";
                response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&error=" + response.encodeURL(error));
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
            error = e.getMessage();
        }
        if (!error.isEmpty()) {
            response.getWriter().write(error);
        } else {
            response.getWriter().write(poiId);
        }
    }
}