package myHikeJava;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Hike;
import models.Month;
import models.Region;

import javax.persistence.EntityManager;

@WebServlet(name = "editHikeServlet", value = "/editHikeServlet")
@MultipartConfig
public class EditHikeServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";
        try {
            //Create new hike object based on the data entered in create.jsp
            Hike hike = getUpdatedHike(request);
            //Insert hike into database
            Database.update(hike);

        } catch (IOException | ServletException e) {
            error = e.getMessage();
        }
        response.sendRedirect("create.jsp?error=" + response.encodeURL(error));
    }

    private Hike getUpdatedHike(HttpServletRequest request) throws IOException, ServletException {
        Hike hike = Database.getHikeById(request.getParameter("Id"));

        //Get values from parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal startLon = new BigDecimal(request.getParameter("startLon"));
        BigDecimal startLat = new BigDecimal(request.getParameter("startLat"));
        BigDecimal endLon = new BigDecimal(request.getParameter("endLon"));
        BigDecimal endLat = new BigDecimal(request.getParameter("endLat"));

        //Distance and altitude can be empty or null, therefore we need to check for this, if they are null or empty
        //we cannot cast the given String to BigDecimal or Integer.
        BigDecimal distance = getDistance(request);
        Integer altitude = getAltitude(request);

        //Star ratings can just be parsed to Integer
        int landscapeRating = Integer.parseInt(request.getParameter("landscape-rating"));
        int strengthRating = Integer.parseInt(request.getParameter("strength-rating"));
        int staminaRating = Integer.parseInt(request.getParameter("stamina-rating"));
        int difficultyRating = Integer.parseInt(request.getParameter("difficulty-rating"));

        //Get the proper region from the database based on the given regionId (primary key is name)
        Region region = Database.getRegionById(request.getParameter("region"));

        //Duration needs to be properly formatted with DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Time duration = Time.valueOf(LocalTime.parse(request.getParameter("duration"), formatter));

        //Get recommended Months are String[] from html parameter and turn them into a Bitmap
        String recommendedMonths = Month.getBitmapFromMonths(request.getParameterValues("months"));

        //Encode image to Base64 String
        String image = encodeToBase64(request);

        //Populate hike object with formatted/adjusted parameter data.
        hike = new Hike(hike.getHikeId(), name, description, startLon, startLat, endLon, endLat, duration, altitude, distance,
                staminaRating, strengthRating, difficultyRating, landscapeRating, image, recommendedMonths, region, false);
        return hike;
    }

    //Attempts to encode the given file to a base64 String (doesn't need to check if it's png, jpg, jpeg, as this is
    //already validated in create.js -> function validateForm()
    private String encodeToBase64(HttpServletRequest request) throws IOException, ServletException {
        Part fileToUpload = request.getPart("fileToUpload");
        if (fileToUpload.getSize() != 0) {
            try (InputStream is = fileToUpload.getInputStream();
                 ByteArrayOutputStream os = new ByteArrayOutputStream()) {

                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }

                return Base64.getEncoder().encodeToString(os.toByteArray());
            }
        } else {
            return request.getParameter("oldImage");
        }
    }

    //getAltitude(HttpServletRequest request) returns the altitude entered into our create.jsp inputs, if the value of
    //this input field is empty or null it will return null -> TODO replace "return null" with calculation
    private Integer getAltitude(HttpServletRequest request) {
        String altitudeString = request.getParameter("altitude");

        if (altitudeString != null && !altitudeString.isEmpty()) {
            return Integer.parseInt(altitudeString);
        }
        else {
            return null;
        }
    }

    //getDistance(HttpServletRequest request) returns the distance entered into our create.jsp inputs, if the value of
    //this input field is empty or null it will return null -> TODO replace "return null" with calculation
    private BigDecimal getDistance(HttpServletRequest request) {
        String distanceString = request.getParameter("distance");
        if (distanceString != null && !distanceString.isEmpty()) {
            return new BigDecimal(distanceString);
        }
        else {
            return null;
        }
    }
}