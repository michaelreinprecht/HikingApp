package servlets.hikeServlets;

import database.Database;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import models.Hike;
import models.Region;
import models.User;
import servlets.ServletUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//Contains basic methods useful for most Hike related servlets.
public class HikeServletUtils extends ServletUtils {

    //Fills (at least the basic) values passed in the request into a hike object.
    protected Hike getHikeBase(HttpServletRequest request, Hike hike) {
        //Get values from parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String routeCoordinates = request.getParameter("route-coordinates");

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
        HttpSession session = request.getSession();
        String hikeOfUser = (String) session.getAttribute("username");
        User user = Database.getUserById(hikeOfUser);
        //Populate hike object with formatted/adjusted parameter data.
        hike = new Hike(hike.getHikeId(), name, description, routeCoordinates, duration, altitude, distance,
                staminaRating, strengthRating, difficultyRating, landscapeRating, null, null, region, new ArrayList<>(), new ArrayList<>(),false, user);

        return hike;
    }

    //getAltitude(HttpServletRequest request) returns the altitude entered into our create.jsp inputs, if the value of
    //this input field is empty or null it will return null
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
    //this input field is empty or null it will return null
    protected BigDecimal getDistance(HttpServletRequest request) {
        String distanceString = request.getParameter("distance");
        if (distanceString != null && !distanceString.isEmpty()) {
            return new BigDecimal(distanceString);
        }
        else {
            return null;
        }
    }
}
