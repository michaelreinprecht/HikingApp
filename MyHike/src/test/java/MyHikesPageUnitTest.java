import database.Database;
import models.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class MyHikesPageUnitTest {
    @Test
    void getHikesByUser(){
        // the myhikestest user has two hikes in the database, one that is deleted and one that is not deleted.
        List<Hike> hikes = Database.getHikesByUser("myhikestest");
        List<Hike> expectedHikes = new ArrayList<>(){
            {
                add(Database.getHikeById("3790f43d-0ae0-4ae7-a5c4-75dc494d8412"));
            }
        };
        Assert.assertEquals(expectedHikes, hikes);

    }
}
