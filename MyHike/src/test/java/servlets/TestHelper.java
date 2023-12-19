package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestHelper {
    protected Hike getExpectedHike() {
        List<PointOfInterest> pois = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Time duration = Time.valueOf(LocalTime.parse("01:00", formatter));
        Hike expectedHike;
        expectedHike = new Hike(
                "test1",
                "Testing",
                "Testing",
                "[[47.324426,10.817871],[47.318965,10.825028]]",
                duration, 111,
                new BigDecimal("1.11"),
                3,
                3,
                3,
                3,
                "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAABhGlDQ1BJQ0MgcHJvZmlsZQAAKJF9kT1Iw0AcxV9TpSotDnYo4pChdrIgKuIoVSyChdJWaNXB5NIvaGJIUlwcBdeCgx+LVQcXZ10dXAVB8APE1cVJ0UVK/F9SaBHjwXE/3t173L0DhGaNqWbPOKBqlpFJJsR8YUUMvCKIEPoRQUxipp7KLuTgOb7u4ePrXZxneZ/7c4SUoskAn0g8y3TDIl4nnt60dM77xGFWkRTic+Ixgy5I/Mh12eU3zmWHBZ4ZNnKZOeIwsVjuYrmLWcVQiaeIo4qqUb6Qd1nhvMVZrdVZ+578hcGitpzlOs0RJLGIFNIQIaOOKmqwEKdVI8VEhvYTHv5hx58ml0yuKhg55rEBFZLjB/+D392apckJNymYAHpfbPtjFAjsAq2GbX8f23brBPA/A1dax7/RBGY+SW90tOgRMLgNXFx3NHkPuNwBIk+6ZEiO5KcplErA+xl9UwEYugUGVt3e2vs4fQBy1NXSDXBwCMTKlL3m8e6+7t7+PdPu7weBp3KtjjzVbQAAAAZiS0dEAAAAAAAA+UO7fwAAAAlwSFlzAAAuIwAALiMBeKU/dgAAAAd0SU1FB+cMDQkaErxKBFoAAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAAARElEQVQY02P8V8zxn4EIwMJozkAUYPz/n0gTP/9kJcpEFsv9EsQp/PWfmTg38p9/S5wbf7IIEWe11tZ7xCnk2vKaKIUA/pQSEcYhZyYAAAAASUVORK5CYII=",
                "111000111000",
                new Region("Bregenzerwald"),
                comments,
                pois,
                false,
                new User("admin", "$2a$10$FY20m9VBfZb3LcGaR.Z7/uGci6CXLIQkuvpDqEm31sjWpDCAaWpIq", true, null));
        return expectedHike;
    }

    protected User getExpectedUser() {
        User expectedUser;
        List<Hike> hikes = new ArrayList<>();
        hikes.add(getExpectedHike());
        expectedUser = new User("admin", "$2a$10$FY20m9VBfZb3LcGaR.Z7/uGci6CXLIQkuvpDqEm31sjWpDCAaWpIq", true, hikes);
        return expectedUser;
    }

    protected Comment getExpectedComment() {
        Comment expectedComment;
        expectedComment = new Comment(
                "commentTest1",
                "Testing",
                getExpectedHike(),
                getExpectedUser()
        );
        return expectedComment;
    }

    protected HttpServletRequest getMockedRequest() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("name")).thenReturn("Testing");
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

        Part filePart = mock(Part.class);
        when(filePart.getContentType()).thenReturn("image/png");
        when(filePart.getInputStream()).thenReturn(new FileInputStream(new File("src/test/resources/Base64Test.png")));
        when(filePart.getSize()).thenReturn((long) new File("src/test/resources/Base64Test.png").length());
        when(filePart.getName()).thenReturn("fileToUpload");
        when(request.getPart("fileToUpload")).thenReturn(filePart);

        //Mock session
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("username")).thenReturn("admin");
        when(mockSession.getAttribute("isAdmin")).thenReturn(true);
        when(request.getSession()).thenReturn(mockSession);

        return request;
    }

    protected HttpServletResponse getMockedResponse() throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        return response;
    }
}
