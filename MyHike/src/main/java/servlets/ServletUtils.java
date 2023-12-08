package servlets;

import database.Database;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import models.Hike;
import models.Region;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class ServletUtils extends HttpServlet {

    //getAltitude(HttpServletRequest request) returns the altitude entered into our create.jsp inputs, if the value of
    //this input field is empty or null it will return null -> TODO replace "return null" with calculation
    protected Integer getAltitude(HttpServletRequest request) {
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
    protected BigDecimal getDistance(HttpServletRequest request) {
        String distanceString = request.getParameter("distance");
        if (distanceString != null && !distanceString.isEmpty()) {
            return new BigDecimal(distanceString);
        }
        else {
            return null; //TODO replace with calculation
        }
    }

    protected String getBase64(Part fileToUpload) throws IOException {
        try (InputStream is = fileToUpload.getInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            return Base64.getEncoder().encodeToString(os.toByteArray());
        }
    }

    protected Hike getHikeBase(HttpServletRequest request, Hike hike) {
        //Get values from parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String markerCoordinates = request.getParameter("route-coordinates");

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

        //Populate hike object with formatted/adjusted parameter data.
        hike = new Hike(hike.getHikeId(), name, description, markerCoordinates, duration, altitude, distance,
                staminaRating, strengthRating, difficultyRating, landscapeRating, null, null, region, null,false, null);

        return hike;
    }
}
