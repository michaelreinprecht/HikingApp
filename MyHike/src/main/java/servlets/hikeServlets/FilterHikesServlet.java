package servlets.hikeServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Hike;
import models.Month;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import static database.Database.getAllHikes;

@WebServlet("/filterHikesServlet")
public class FilterHikesServlet extends HttpServlet {
    private String error = "";
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        filterHikes(request, response);
    }

    //Filters the hikes according to the filter parameters passed in the request.
    protected void filterHikes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get all hikes from database
        List<Hike> hikes = getAllHikes();
        //Filter hikes according to filter values in request parameters
        hikes = filterHikesByRequest(hikes, request);
        //Keep the filter values in the input fields
        saveFilterValuesInSession(request);
        //Set filtered hikes as attribute for new request
        request.setAttribute("filteredHikes", hikes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("hikelist.jsp");
        if (error != null && !error.isEmpty()) {
            request.setAttribute("error", error);
        }
        dispatcher.forward(request, response);
    }

    protected void saveFilterValuesInSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.setAttribute("durationFilter", request.getParameter("durationFilter"));
        session.setAttribute("distanceFilter", request.getParameter("distanceFilter"));
        session.setAttribute("staminaFilter", request.getParameter("staminaFilter"));
        session.setAttribute("strengthFilter", request.getParameter("strengthFilter"));
        session.setAttribute("landscapeFilter", request.getParameter("landscapeFilter"));
        session.setAttribute("altitudeFilter", request.getParameter("altitudeFilter"));
        session.setAttribute("difficultyFilter", request.getParameter("difficultyFilter"));
        session.setAttribute("searchQuery", request.getParameter("searchQuery"));
        String[] selectedMonths = request.getParameterValues("monthFilter");
        if (selectedMonths != null && selectedMonths.length > 0) {
            session.setAttribute("selectedMonths", selectedMonths);
        } else {
            // Wenn keine Monate ausgewählt sind, standardmäßig alle auswählen
            session.setAttribute("selectedMonths", models.Month.ALL_MONTHS);
        }
    }

    protected List<Hike> filterHikesBySearchQuery(List<Hike> hikes, String searchQuery) {
        if (searchQuery != null && !searchQuery.isEmpty()) {
            hikes = hikes.stream()
                    .filter(hike ->
                            hike.getHikeRegion().getRegionName().toLowerCase().contains(searchQuery.toLowerCase()) ||   //Search Region
                                    hike.getHikeName().toLowerCase().contains(searchQuery.toLowerCase()))   //Search Name
                    .collect(Collectors.toList());
        }
        return hikes;
    }

    protected List<Hike> filterHikesByDuration(List<Hike> hikes, String durationFilter) {
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
                return hikes;
            } catch (IllegalArgumentException e) {
                error = "Error: Duration is not a valid time format.";
            }
        }
        return hikes;
    }

    protected List<Hike> filterHikesByDistance(List<Hike> hikes, String distanceFilter) {
        if (distanceFilter != null && !distanceFilter.isEmpty() && !distanceFilter.equals("0")) {
            try {
                BigDecimal distance = new BigDecimal(distanceFilter);
                hikes = hikes.stream()
                        .filter(hike -> {
                            BigDecimal hikeDistance = hike.getHikeDistance();
                            return hikeDistance != null &&
                                    hikeDistance.compareTo(distance) <= 0;
                        })
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                error = "Error: Distance filter does not hold a valid distance value.";
            }
        }
        return hikes;
    }

    protected List<Hike> filterHikesByAltitude(List<Hike> hikes, String altitudeFilter) {
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
                error = "Error: Altitude filter does not hold a valid altitude value.";
            }
        }
        return hikes;
    }

    protected List<Hike> filterHikesByStamina(List<Hike> hikes, String staminaFilter) {
        try {
            if (staminaFilter != null && !staminaFilter.isEmpty() && !staminaFilter.equals("0")) {
                int stamina = Integer.parseInt(staminaFilter);
                hikes = hikes.stream().filter(hike -> hike.getHikeStamina() <= stamina).collect(Collectors.toList());
            }
        } catch (NumberFormatException e) {
            error = "Error: Level of fitness filter does not hold a valid fitness value.";
        }
        return hikes;
    }

    protected List<Hike> filterHikesByStrength(List<Hike> hikes, String strengthFilter) {
        try {
            if (strengthFilter != null && !strengthFilter.isEmpty() && !strengthFilter.equals("0")) {
                int strength = Integer.parseInt(strengthFilter);
                hikes = hikes.stream().filter(hike -> hike.getHikeStrength() <= strength).collect(Collectors.toList());
            }
        } catch (NumberFormatException e) {
            error = "Error: Strength rating filter does not hold a valid strength value.";
        }
        return hikes;
    }

    protected List<Hike> filterHikesByLandscape(List<Hike> hikes, String landscapeFilter) {
        if (landscapeFilter != null && !landscapeFilter.isEmpty() && !landscapeFilter.equals("0")) {
            try {
                int landscape = Integer.parseInt(landscapeFilter);
                hikes = hikes.stream().filter(hike -> hike.getHikeLandscape() >= landscape).collect(Collectors.toList());
            } catch (NumberFormatException e) {
                error = "Error: Landscape rating filter does not hold a valid landscape rating.";
            }
        }
        return hikes;
    }

    protected List<Hike> filterHikesByDifficulty(List<Hike> hikes, String difficultyFilter) {
        if (difficultyFilter != null && !difficultyFilter.isEmpty() && !difficultyFilter.equals("0")) {
            try {
                int difficulty = Integer.parseInt(difficultyFilter);
                hikes = hikes.stream().filter(hike -> hike.getHikeDifficulty() <= difficulty).collect(Collectors.toList());
            } catch (NumberFormatException e) {
                error = "Error: Difficulty rating filter does not hold a valid difficulty rating.";
            }
        }
        return hikes;
    }

    protected List<Hike> filterHikesByMonths(List<Hike> hikes, String monthFilter) {
        if (monthFilter != null && !monthFilter.isEmpty()) {
            try {
                int monthFilterInt = Integer.parseInt(monthFilter, 2);

                hikes = hikes.stream()
                        .filter(hike -> hasSelectedMonth(hike, monthFilterInt))
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                error = "Error: Something went wrong when selecting the months in the month filter.";
            }
        }
        return hikes;
    }

    //Applies all filter methods for the current request
    protected List<Hike> filterHikesByRequest(List<Hike> hikes, HttpServletRequest request) {
        String searchQuery = request.getParameter("searchQuery"); // Get search query from searchbar.
        String durationFilter = request.getParameter("durationFilter");
        String distanceFilter = request.getParameter("distanceFilter");
        String staminaFilter = request.getParameter("staminaFilter");
        String strengthFilter = request.getParameter("strengthFilter");
        String landscapeFilter = request.getParameter("landscapeFilter");
        String altitudeFilter = request.getParameter("altitudeFilter");
        String difficultyFilter = request.getParameter("difficultyFilter");
        String monthFilter = Month.getBitmapFromMonths(request.getParameterValues("monthFilter"));

        hikes = filterHikesBySearchQuery(hikes, searchQuery);
        hikes = filterHikesByDuration(hikes, durationFilter);
        hikes = filterHikesByDistance(hikes, distanceFilter);
        hikes = filterHikesByAltitude(hikes, altitudeFilter);
        hikes = filterHikesByStamina(hikes, staminaFilter);
        hikes = filterHikesByStrength(hikes, strengthFilter);
        hikes = filterHikesByLandscape(hikes, landscapeFilter);
        hikes = filterHikesByDifficulty(hikes, difficultyFilter);
        hikes = filterHikesByMonths(hikes, monthFilter);
        return hikes;
    }

    protected boolean hasSelectedMonth(Hike hike, int monthFilter) {
        // Assuming hike.getMonths() returns the 12-bit bitmap as a string
        String hikeMonths = hike.getHikeMonths();

        // Iterate through each bit in the monthFilter
        for (int i = 0; i < hikeMonths.length(); i++) {
            // Check if the corresponding bit in hikeMonths is '1'
            if (((monthFilter >> (11 - i)) & 1) == 1 && hikeMonths.charAt(i) == '1') {
                return true; // Hike has at least one recommended month
            }
        }
        return false; // Hike has no recommended months in the monthFilter
    }

    public String getError() {
        return error;
    }
}
