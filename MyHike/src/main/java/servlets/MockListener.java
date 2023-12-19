package servlets;

import database.Database;
import facade.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import models.Hike;
import models.User;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebListener
public class MockListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        String mockIt = System.getenv("MOCK");
        if (mockIt!=null) {
            JPAFacade mockFacade = mock(JPAFacade.class);
            JPAHikeFacade mockHikeFacade = mock(JPAHikeFacade.class);
            JPAPointOfInterestFacade mockPointOfInterestFacade = mock(JPAPointOfInterestFacade.class);
            JPARegionFacade mockRegionFacade = mock(JPARegionFacade.class);
            JPAUserFacade mockUserFacade = mock(JPAUserFacade.class);
            JPACommentFacade mockCommentFacade = mock(JPACommentFacade.class);

            Database.facade = mockFacade;
            Database.hikeFacade = mockHikeFacade;
            Database.pointOfInterestFacade = mockPointOfInterestFacade;
            Database.regionFacade = mockRegionFacade;
            Database.userFacade = mockUserFacade;
            Database.commentFacade = mockCommentFacade;

            //Return testing user.
            when(Database.userFacade.getUserById("admin")).thenReturn(new User("admin", "$2a$10$FY20m9VBfZb3LcGaR.Z7/uGci6CXLIQkuvpDqEm31sjWpDCAaWpIq", true, null));

            //Return testing hikes.
            List<Hike> hikes = new ArrayList<>();
            when(Database.hikeFacade.getAllHikes()).thenReturn(null);
        }
    }
}