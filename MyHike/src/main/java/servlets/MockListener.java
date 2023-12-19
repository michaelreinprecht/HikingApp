package servlets;

import database.Database;
import facade.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import models.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@WebListener
public class MockListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        String mockIt = System.getenv("MOCK");
        if (mockIt!=null) {
            JPAFacade mockFacade = mock(JPAFacade.class);
            JPAHikeFacade mockHikeFacade = mock(JPAHikeFacade.class);
            JPAPointOfInterestFacade mockPointOfInterestFacade = mock(JPAPointOfInterestFacade.class);
            JPAUserFacade mockUserFacade = mock(JPAUserFacade.class);
            JPACommentFacade mockCommentFacade = mock(JPACommentFacade.class);

            Database.facade = mockFacade;
            Database.hikeFacade = mockHikeFacade;
            Database.pointOfInterestFacade = mockPointOfInterestFacade;
            //Database.userFacade = mockUserFacade;
            Database.commentFacade = mockCommentFacade;

            //Return testing user.
            //User expectedUser = getExpectedUser();
            //when(Database.userFacade.getUserById("admin")).thenReturn(expectedUser);

            //Return testing hikes.
            List<Hike> expectedHikes = new ArrayList<>();
            Hike expectedHike = getExpectedHike();
            expectedHikes.add(expectedHike);
            when(Database.hikeFacade.getAllHikes()).thenReturn(expectedHikes);
            when(Database.hikeFacade.getHikeById(any(String.class))).thenReturn(expectedHike);
        }
    }

    private Hike getExpectedHike() {
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
                new Region("Montafon"),
                comments,
                pois,
                false,
                new User("admin", "admin", true, null));
        return expectedHike;
    }

    private User getExpectedUser() {
        User expectedUser;
        expectedUser= new User("admin", "$2a$10$FY20m9VBfZb3LcGaR.Z7/uGci6CXLIQkuvpDqEm31sjWpDCAaWpIq", true, null);
        return expectedUser;
    }
}