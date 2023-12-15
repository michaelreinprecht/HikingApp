package servlets.hikeServlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateHikeServletTest {
    CreateHikeServlet createHikeServlet = new CreateHikeServlet();

    @Test
    void createHike() {
    }

    @Test
    void getHike() {
    }

    @Test
    void handleAuth() {


    }

    private HttpServletRequest getMockedRequest() {
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


        return request;
    }

    private HttpServletResponse getMockedResponse() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        return response;
    }
}