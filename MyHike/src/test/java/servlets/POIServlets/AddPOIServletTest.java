package servlets.POIServlets;

import database.Database;
import facade.JPAFacade;
import facade.JPAHikeFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Hike;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AddPOIServletTest extends TestHelper {
    private final AddPOIServlet addPOIServlet = new AddPOIServlet();
    @Test
    void addPOI() throws ServletException, IOException {
        //Mock database
        Database.facade = mock(JPAFacade.class);
        Database.hikeFacade = mock(JPAHikeFacade.class);
        Hike expectedHike = getExpectedHike();
        when(Database.hikeFacade.getHikeById(any(String.class))).thenReturn(expectedHike);

        HttpServletRequest request = getMockedRequest();
        HttpServletResponse response = getMockedResponse();

        addPOIServlet.addPOI(request, response);
        assertTrue(addPOIServlet.getError().isEmpty());
    }

    @Override
    protected HttpServletRequest getMockedRequest() throws ServletException, IOException {
        HttpServletRequest request = super.getMockedRequest();
        when(request.getParameter("hikeId")).thenReturn("test1");
        when(request.getParameter("poiTitle")).thenReturn("Testing");
        when(request.getParameter("poiDescription")).thenReturn("Testing");
        when(request.getParameter("poiLon")).thenReturn("10.10");
        when(request.getParameter("poiLat")).thenReturn("10.10");

        //Mock the image in the Part
        Part mockPart = mock(Part.class);
        when(request.getPart("poiImage")).thenReturn(mockPart);
        Path imagePath = Paths.get("src/test/resources/Base64Test.png");
        byte[] imageContent = Files.readAllBytes(imagePath);
        when(mockPart.getInputStream()).thenReturn(new ByteArrayInputStream(imageContent));

        return request;
    }

    @Override
    protected HttpServletResponse getMockedResponse() throws IOException {
        HttpServletResponse response = super.getMockedResponse();

        // Stub the getWriter() method to return a valid PrintWriter
        PrintWriter mockPrintWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(mockPrintWriter);

        return response;
    }
}