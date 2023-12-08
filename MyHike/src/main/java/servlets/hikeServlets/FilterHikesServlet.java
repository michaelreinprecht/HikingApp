package servlets.hikeServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Hike;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import static database.Database.getAllHikes;

@WebServlet("/filterHikesServlet")
public class FilterHikesServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Hike> hikes = getAllHikes();
        String durationFilter = request.getParameter("durationFilter");
        String distanceFilter = request.getParameter("distanceFilter");
        String staminaFilter = request.getParameter("staminaFilter");
        String strengthFilter = request.getParameter("strengthFilter");
        String landscapeFilter = request.getParameter("landscapeFilter");
        String altitudeFilter = request.getParameter("altitudeFilter");
        String difficultyFilter = request.getParameter("difficultyFilter");
        String monthFilter = request.getParameter("monthFilter");

        String searchQuery = request.getParameter("searchQuery"); // Get search query from searchbar.
        if (searchQuery != null && !searchQuery.isEmpty()) {
            hikes = hikes.stream()
                    .filter(hike ->
                            hike.getHikeRegion().getRegionName().toLowerCase().contains(searchQuery.toLowerCase()) ||   //Search Region
                                    hike.getHikeName().toLowerCase().contains(searchQuery.toLowerCase()))   //Search Name
                    .collect(Collectors.toList());
        }

        if (durationFilter != null && !durationFilter.isEmpty() && !durationFilter.equals("0")) {
            try {
                String formattedDuration = durationFilter + ":00";
                Time duration = Time.valueOf(formattedDuration);

                hikes = hikes.stream()
                        .filter(hike ->
                                hike.getHikeDuration() != null &&
                                        !hike.getHikeDuration().toLocalTime().isAfter(duration.toLocalTime())
                        )
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                System.err.println("Error: Duration is not a valid time format.");
            }
        }

        if (distanceFilter != null && !distanceFilter.isEmpty() && !distanceFilter.equals("0")) {
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
                System.err.println("Error: Distance filter does not hold a valid distance.");
            }
        }

        if (altitudeFilter != null && !altitudeFilter.isEmpty() && !altitudeFilter.equals("0")) {
            try {
                int altitude = Integer.parseInt(altitudeFilter);

                hikes = hikes.stream()
                        .filter(hike -> {
                            int hikeAltitude = hike.getHikeAltitude();
                            return hikeAltitude >= 0 &&
                                    hikeAltitude <= altitude;
                        })
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.err.println("Fehler: difficultyFilter ist keine gültige Zahl");
            }
        }


        if (staminaFilter != null && !staminaFilter.isEmpty() && !staminaFilter.equals("0")) {
            int stamina = Integer.parseInt(staminaFilter);
            hikes = hikes.stream().filter(hike -> hike.getHikeStamina() == stamina).collect(Collectors.toList());
        }

        if (strengthFilter != null && !strengthFilter.isEmpty() && !strengthFilter.equals("0")) {
            int strength = Integer.parseInt(strengthFilter);
            hikes = hikes.stream().filter(hike -> hike.getHikeStrength() == strength).collect(Collectors.toList());
        }

        if (landscapeFilter != null && !landscapeFilter.isEmpty() && !landscapeFilter.equals("0")) {
            try {
                int landscape = Integer.parseInt(landscapeFilter);
                hikes = hikes.stream().filter(hike -> hike.getHikeLandscape() == landscape).collect(Collectors.toList());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (difficultyFilter != null && !difficultyFilter.isEmpty() && !difficultyFilter.equals("0")) {
            try {
                int difficulty = Integer.parseInt(difficultyFilter);
                hikes = hikes.stream().filter(hike -> hike.getHikeDifficulty() == difficulty).collect(Collectors.toList());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (monthFilter != null && !monthFilter.isEmpty()) {
            try {
                int month = Integer.parseInt(monthFilter);
                hikes = hikes.stream()
                        .filter(hike -> hasSelectedMonth(hike, month))
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("filteredHikes", hikes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("hikelist.jsp");
        dispatcher.forward(request, response);
    }

    private boolean hasSelectedMonth(Hike hike, int selectedMonth) {
        String hikeMonthsBitmap = hike.getHikeMonths(); // Replace with the actual method to get the months bitmap

        return selectedMonth >= 0 && selectedMonth < hikeMonthsBitmap.length() && hikeMonthsBitmap.charAt(selectedMonth) == '1';
    }
}
