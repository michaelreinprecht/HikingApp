package models;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonthTest {

    @Test
    void getMonthsByBitmap() {
        String bitmap = "101010101010"; //Equal to January, March, May, Juli, September, November
        String[] months = Month.getMonthsByBitmap(bitmap);
        String[] expectedResult = new String[] { "January",  null ,"March", null, "May", null, "July", null, "September", null,"November", null};
        assertArrayEquals(months, expectedResult);
    }

    @Test
    void getBitmapFromMonths() {
        String[] months = new String[] { "January",  null ,"March", null, "May", null, "July", null, "September", null,"November", null};
        String expectedResult = "101010101010";
        String bitmap = Month.getBitmapFromMonths(months);
        assertEquals(bitmap, expectedResult);
    }
}