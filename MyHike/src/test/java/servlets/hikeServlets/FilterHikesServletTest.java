package servlets.hikeServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import models.Hike;
import models.Region;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilterHikesServletTest extends TestHelper {
    private final FilterHikesServlet filterHikesServlet = new FilterHikesServlet();
    private final Hike expectedHike = getExpectedHike();
    @Test
    void saveFilterValuesInSession() {
        HttpServletRequest request = getMockedRequest();
        filterHikesServlet.saveFilterValuesInSession(request);
    }

    @Test
    void filterHikesBySearchQuery() {
        List<Hike> hikes = getHikesToFilter();

        //Case: Filtering by name - expected hike is not filtered out
        hikes = filterHikesServlet.filterHikesBySearchQuery(hikes, "Test");
        assertTrue((hikes.contains((expectedHike)) && hikes.size() == 1));

        //Case: Filtering by name - expected hike is filtered out
        hikes = filterHikesServlet.filterHikesBySearchQuery(hikes, "NotAHikeName");
        assertFalse(hikes.contains((expectedHike)));
        assertTrue(filterHikesServlet.getError().isEmpty());

        //Repopulate list:
        hikes = getHikesToFilter();

        //Case: Filtering by region - expected hike is not filtered out
        hikes = filterHikesServlet.filterHikesBySearchQuery(hikes, "Bregenzerwald");
        assertTrue((hikes.contains((expectedHike)) && hikes.size() == 1));

        //Case: Filtering by region - expected hike is filtered out
        hikes = filterHikesServlet.filterHikesBySearchQuery(hikes, "Montafon");
        assertFalse(hikes.contains((expectedHike)));
        assertTrue(filterHikesServlet.getError().isEmpty());
    }

    @Test
    void filterHikesByDuration() {
        List<Hike> hikes = getHikesToFilter();

        //Case: expected hike is not filtered out
        hikes = filterHikesServlet.filterHikesByDuration(hikes, "01:00");
        assertTrue((hikes.contains((expectedHike)) && hikes.size() == 1));

        //Case: expected hike is filtered out
        hikes = filterHikesServlet.filterHikesByDuration(hikes, "00:30");
        assertFalse(hikes.contains((expectedHike)));
        assertTrue(filterHikesServlet.getError().isEmpty());
    }

    @Test
    void filterHikesByDistance() {
        List<Hike> hikes = getHikesToFilter();

        //Case: expected hike is not filtered out
        hikes = filterHikesServlet.filterHikesByDistance(hikes, "2");
        assertTrue((hikes.contains((expectedHike)) && hikes.size() == 1));

        //Case: expected hike is filtered out
        hikes = filterHikesServlet.filterHikesByDistance(hikes, "1");
        assertFalse(hikes.contains((expectedHike)));
        assertTrue(filterHikesServlet.getError().isEmpty());
    }

    @Test
    void filterHikesByAltitude() {
        List<Hike> hikes = getHikesToFilter();

        //Case: expected hike is not filtered out
        hikes = filterHikesServlet.filterHikesByAltitude(hikes, "200");
        assertTrue((hikes.contains((expectedHike)) && hikes.size() == 1));

        //Case: expected hike is filtered out
        hikes = filterHikesServlet.filterHikesByAltitude(hikes, "100");
        assertFalse(hikes.contains((expectedHike)));
        assertTrue(filterHikesServlet.getError().isEmpty());
    }

    @Test
    void filterHikesByStamina() {
        List<Hike> hikes = getHikesToFilter();

        //Case: expected hike is not filtered out
        hikes = filterHikesServlet.filterHikesByStamina(hikes, "3");
        assertTrue((hikes.contains((expectedHike)) && hikes.size() == 1));

        //Case: expected hike is filtered out
        hikes = filterHikesServlet.filterHikesByStamina(hikes, "2");
        assertFalse(hikes.contains((expectedHike)));
        assertTrue(filterHikesServlet.getError().isEmpty());
    }

    @Test
    void filterHikesByStrength() {
        List<Hike> hikes = getHikesToFilter();

        //Case: expected hike is not filtered out
        hikes = filterHikesServlet.filterHikesByStrength(hikes, "3");
        assertTrue((hikes.contains((expectedHike)) && hikes.size() == 1));

        //Case: expected hike is filtered out
        hikes = filterHikesServlet.filterHikesByStrength(hikes, "2");
        assertFalse(hikes.contains((expectedHike)));
        assertTrue(filterHikesServlet.getError().isEmpty());
    }

    @Test
    void filterHikesByLandscape() {
        List<Hike> hikes = getHikesToFilter();

        //Case: expected hike is not filtered out
        hikes = filterHikesServlet.filterHikesByLandscape(hikes, "3");
        assertTrue((hikes.contains((expectedHike)) && hikes.size() == 1));

        //Case: expected hike is filtered out
        hikes = filterHikesServlet.filterHikesByLandscape(hikes, "4");
        assertFalse(hikes.contains((expectedHike)));
        assertTrue(filterHikesServlet.getError().isEmpty());
    }

    @Test
    void filterHikesByDifficulty() {
        List<Hike> hikes = getHikesToFilter();

        //Case: expected hike is not filtered out
        hikes = filterHikesServlet.filterHikesByDifficulty(hikes, "3");
        assertTrue((hikes.contains((expectedHike)) && hikes.size() == 1));

        //Case: expected hike is filtered out
        hikes = filterHikesServlet.filterHikesByDifficulty(hikes, "2");
        assertFalse(hikes.contains((expectedHike)));
        assertTrue(filterHikesServlet.getError().isEmpty());
    }

    @Test
    void filterHikesByMonths() {
        List<Hike> hikes = getHikesToFilter();

        //Case: expected hike is not filtered out (filtering for february)
        hikes = filterHikesServlet.filterHikesByMonths(hikes, "010000000000");
        assertTrue((hikes.contains((expectedHike)) && hikes.size() == 1));

        //Case: expected hike is filtered out (filtering for may)
        hikes = filterHikesServlet.filterHikesByMonths(hikes, "000010000000");
        assertFalse(hikes.contains((expectedHike)));
        assertTrue(filterHikesServlet.getError().isEmpty());
    }

    //Uses all filters based on the mocked request. Supposed to not filter out the expectedHike, but the "hikeToBeFiltered".
    //-> see getHikesToFilter()
    @Test
    void filterHikesByRequest() {
        List<Hike> hikes = getHikesToFilter();
        hikes = filterHikesServlet.filterHikesByRequest(hikes, getMockedRequest());
        assertTrue((hikes.contains((expectedHike)) && hikes.size() == 1));
        assertTrue(filterHikesServlet.getError().isEmpty());
    }

    @Test
    void hasSelectedMonth() {
        //Case: the month is 1 in the bitmap of the hike and therefore recommended
        boolean februaryRecommended = filterHikesServlet.hasSelectedMonth(expectedHike, Integer.parseInt("010000000000", 2));
        assertTrue(februaryRecommended);

        //Case: the month is 0 in the bitmap of the hike and therefore not recommended
        boolean mayRecommended = filterHikesServlet.hasSelectedMonth(expectedHike, Integer.parseInt("000010000000", 2));
        assertFalse(mayRecommended);
    }

    @Override
    protected HttpServletRequest getMockedRequest() {
        //Mock request
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("durationFilter")).thenReturn("01:00");
        when(request.getParameter("distanceFilter")).thenReturn("2");
        when(request.getParameter("staminaFilter")).thenReturn("3");
        when(request.getParameter("strengthFilter")).thenReturn("3");
        when(request.getParameter("landscapeFilter")).thenReturn("3");
        when(request.getParameter("altitudeFilter")).thenReturn("111");
        when(request.getParameter("difficultyFilter")).thenReturn("3");
        when(request.getParameter("searchQuery")).thenReturn("Test");
        String[] selectedMonths = {"February"};
        when(request.getParameterValues("monthFilter")).thenReturn(selectedMonths);

        //Mock session
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);

        return request;
    }

    private List<Hike> getHikesToFilter() {
        List<Hike> hikes = new ArrayList<>();

        Hike hikeToBeFiltered = new Hike();
        hikeToBeFiltered.setHikeRegion(new Region(""));
        hikeToBeFiltered.setHikeName("");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Time duration = Time.valueOf(LocalTime.parse("10:00", formatter));
        hikeToBeFiltered.setHikeDuration(duration);
        hikeToBeFiltered.setHikeDistance(new BigDecimal("10"));
        hikeToBeFiltered.setHikeAltitude(1000);
        hikeToBeFiltered.setHikeStamina(5);
        hikeToBeFiltered.setHikeStrength(5);
        hikeToBeFiltered.setHikeLandscape(1);
        hikeToBeFiltered.setHikeDifficulty(5);
        hikeToBeFiltered.setHikeMonths("100000000000");

        hikes.add(expectedHike);
        hikes.add(hikeToBeFiltered);

        return hikes;
    }
}