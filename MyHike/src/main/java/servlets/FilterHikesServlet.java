package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Hike;
import models.Month;
import models.PointOfInterest;
import myHikeJava.Database;

import static myHikeJava.Database.getAllHikes;

@WebServlet("/filterHikesServlet")
public class FilterHikesServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Hike> hikes = getAllHikes();
        String durationFilter = request.getParameter("durationFilter");
        String distanceFilter = request.getParameter("distanceFilter");
        String staminaFilter = request.getParameter("staminaFilter");
        String strengthFilter = request.getParameter("strengthFilter");
        String difficultyFilter = request.getParameter("difficultyFilter");
        String landscapeFilter = request.getParameter("landscapeFilter");
        String monateFilter = request.getParameter("monateFilter");

        if (durationFilter != null && !durationFilter.isEmpty()) {
            try {
                String formattedDuration = durationFilter + ":00";
                Time duration = Time.valueOf(formattedDuration);

                hikes = hikes.stream()
                        .filter(hike ->
                                hike.getHikeDuration() != null &&
                                        hike.getHikeDuration().toLocalTime().compareTo(duration.toLocalTime()) <= 0
                        )
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                System.err.println("Fehler: durationFilter ist kein gültiges Zeitformat");
            }
        }

        if (distanceFilter != null && !distanceFilter.isEmpty()) {
            try {
                BigDecimal distance = new BigDecimal(distanceFilter);
                BigDecimal upperBound = distance.add(new BigDecimal("10"));

                hikes = hikes.stream()
                        .filter(hike -> {
                            BigDecimal hikeDistance = hike.getHikeDistance();
                            return hikeDistance != null &&
                                    hikeDistance.compareTo(distance) >= 0 &&
                                    hikeDistance.compareTo(upperBound) <= 0;
                        })
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.err.println("Fehler: distanceFilter ist keine gültige Zahl");
            }
        }


        if (staminaFilter != null && !staminaFilter.isEmpty()) {
            int stamina = Integer.parseInt(staminaFilter);
            hikes = hikes.stream().filter(hike -> hike.getHikeStamina() == stamina).collect(Collectors.toList());
        }

        if (strengthFilter != null && !strengthFilter.isEmpty()) {
            int strength = Integer.parseInt(strengthFilter);
            hikes = hikes.stream().filter(hike -> hike.getHikeStrength() == strength).collect(Collectors.toList());
        }

        if (landscapeFilter != null && !landscapeFilter.isEmpty()) {
            try {
                int landscape = Integer.parseInt(landscapeFilter);
                hikes = hikes.stream().filter(hike -> hike.getHikeLandscape() == landscape).collect(Collectors.toList());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("filteredHikes", hikes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("hikelist.jsp");
        dispatcher.forward(request, response);
    }
}
