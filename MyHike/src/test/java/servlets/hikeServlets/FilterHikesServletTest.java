package servlets.hikeServlets;

import database.Database;
import facade.JPAHikeFacade;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FilterHikesServletTest extends TestHelper {

    @Test
    void filterHikes() {
        //Mock database
        Database.hikeFacade = mock(JPAHikeFacade.class);
        //Might just be to much work to mock this ... I think I'll stick to the small tests for this one.

    }

    @Test
    void saveFilterValuesInSession() {
    }

    @Test
    void filterHikesBySearchQuery() {
    }

    @Test
    void filterHikesByDuration() {
    }

    @Test
    void filterHikesByDistance() {
    }

    @Test
    void filterHikesByAltitude() {
    }

    @Test
    void filterHikesByStamina() {
    }

    @Test
    void filterHikesByStrength() {
    }

    @Test
    void filterHikesByLandscape() {
    }

    @Test
    void filterHikesByDifficulty() {
    }

    @Test
    void filterHikesByMonths() {
    }

    @Test
    void filterHikesByRequest() {
    }

    @Test
    void hasSelectedMonth() {

    }
}