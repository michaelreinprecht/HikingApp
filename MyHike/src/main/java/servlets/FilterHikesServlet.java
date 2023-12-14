package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Hike;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import static myHikeJava.Database.getAllHikes;

@WebServlet("/filterHikesServlet")
public class FilterHikesServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Hike> hikes = getAllHikes();
        String durationFilter = request.getParameter("durationFilter");
        String distanceFilter = request.getParameter("distanceFilter");
        String staminaFilter = request.getParameter("staminaFilter");
        String strengthFilter = request.getParameter("strengthFilter");
        String landscapeFilter = request.getParameter("landscapeFilter");
        String monateFilter = request.getParameter("monateFilter");

        String searchQuery = request.getParameter("searchQuery"); // Holen der Suchanfrage aus der Suchzeile
        if (searchQuery != null && !searchQuery.isEmpty()) {
            hikes = hikes.stream()
                    .filter(hike ->
                            hike.getHikeRegion().getRegionName().toLowerCase().contains(searchQuery.toLowerCase()) ||   //Sucht Region
                                    hike.getHikeName().toLowerCase().contains(searchQuery.toLowerCase()))   //Sucht Name
                    .collect(Collectors.toList()); //Gibt dann die Liste mit den Hikes, die das Suchbegriff im Name oder Region haben
        }

        if (durationFilter != null && !durationFilter.isEmpty() && !durationFilter.equals("0")) {
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
                System.err.println("Fehler: distanceFilter ist keine gültige Zahl");
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

        if (monateFilter != null && !monateFilter.isEmpty()) {

            try {
                int monat = Integer.parseInt(monateFilter);
                hikes = hikes.stream()
                        .filter(hike -> hasSelectedMonth(hike, monat))
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        saveFilterValuesInSession(request);

        request.setAttribute("filteredHikes", hikes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("hikelist.jsp");
        dispatcher.forward(request, response);
    }

    private void saveFilterValuesInSession(HttpServletRequest request) {
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

    private boolean hasSelectedMonth(Hike hike, int selectedMonth) {
        String hikeMonthsBitmap = hike.getHikeMonths(); // Replace with the actual method to get the months bitmap

        return selectedMonth >= 0 && selectedMonth < hikeMonthsBitmap.length() && hikeMonthsBitmap.charAt(selectedMonth) == '1';
    }
}
