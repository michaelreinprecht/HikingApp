package servlets.hikeServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import models.Hike;
import models.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HikeServletUtilsTest {
    private final HikeServletUtils hikeServletUtils = new HikeServletUtils();


    @Test
    void getHikeBase() throws SQLException {
        Hike hike = new Hike();
        //Set a random UUID for the new Hike.
        String id = UUID.randomUUID().toString();
        hike.setHikeId(id);

        //Mock request
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("name")).thenReturn("Test Hike");
        when(request.getParameter("description")).thenReturn("Testing");
        when(request.getParameter("route-coordinates")).thenReturn("[[10,10][10,10]]");
        when(request.getParameter("distance")).thenReturn("1.11");
        when(request.getParameter("altitude")).thenReturn("111");
        when(request.getParameter("landscape-rating")).thenReturn("5");
        when(request.getParameter("strength-rating")).thenReturn("5");
        when(request.getParameter("stamina-rating")).thenReturn("5");
        when(request.getParameter("difficulty-rating")).thenReturn("5");
        when(request.getParameter("region")).thenReturn("Bregenzerwald");
        when(request.getParameter("duration")).thenReturn("01:00");

        //Mock session
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("username")).thenReturn("admin");
        when(request.getSession()).thenReturn(mockSession);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Time duration = Time.valueOf(LocalTime.parse("01:00", formatter));

        Hike result = hikeServletUtils.getHikeBase(request, hike);
        Hike expectedHike = new Hike(id, "Test Hike", "Testing", "[[10,10][10,10]]", duration, 111, new BigDecimal("1.11"), 5, 5, 5, 5, null, null, result.getHikeRegion(), new ArrayList<>(), new ArrayList<>(), false, new User("admin", "admin", true, null));
        assertEquals(result.toString(), expectedHike.toString());
    }

    @Test
    void getAltitude() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("altitude")).thenReturn("111");
        Integer altitude = hikeServletUtils.getAltitude(request);
        Integer expectedAltitude = 111;
        assertEquals(expectedAltitude, altitude);
    }

    @Test
    void getDistance() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("distance")).thenReturn("1.11");
        BigDecimal distance = hikeServletUtils.getDistance(request);
        BigDecimal expectedDistance = new BigDecimal("1.11");
        assertEquals(expectedDistance, distance);
    }
}