package servlets.POIServlets;

import database.Database;
import facade.JPAFacade;
import facade.JPAHikeFacade;
import facade.JPAPointOfInterestFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Hike;
import models.PointOfInterest;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeletePOIServletTest extends TestHelper {
    private final DeletePOIServlet deletePOIServlet = new DeletePOIServlet();
    @Test
    void deletePOI() throws ServletException, IOException {
        //Mock database
        Database.facade = mock(JPAFacade.class);
        Database.hikeFacade = mock(JPAHikeFacade.class);
        Database.pointOfInterestFacade = mock(JPAPointOfInterestFacade.class);

        Hike expectedHike = getExpectedHike();
        when(Database.hikeFacade.getHikeById(any(String.class))).thenReturn(expectedHike);

        PointOfInterest expectedPoi = getExpectedPOI();
        when(Database.pointOfInterestFacade.getPointOfInterestById(any(String.class))).thenReturn(expectedPoi);

        HttpServletRequest request = getMockedRequest();
        HttpServletResponse response = getMockedResponse();

        deletePOIServlet.deletePOI(request, response);
        assertTrue(deletePOIServlet.getError().isEmpty());
    }

    @Override
    protected HttpServletRequest getMockedRequest() throws ServletException, IOException {
        HttpServletRequest request = super.getMockedRequest();
        when(request.getParameter("poiId")).thenReturn("poiTest1");

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

    @Override
    protected Hike getExpectedHike() {
        Hike hike = super.getExpectedHike();
        hike.getHikePointsOfInterest().add(getExpectedPOI());
        return hike;
    }

    private PointOfInterest getExpectedPOI() {
        PointOfInterest expectedPoi;
        expectedPoi = new PointOfInterest(
                "poiTest1",
                "Testing",
                "Testing",
                new BigDecimal("10.10"),
                new BigDecimal("10.10"),
                null,
                super.getExpectedHike()
        );
        return expectedPoi;
    }
}