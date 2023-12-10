import models.Month;
import org.junit.Assert;
import org.junit.Test;

public class MonthConvertionTest {
    @Test
    public void getMonthsByBitmap() {
        String bitmap = "101010101010"; //Equal to January, March, May, Juli, September, November
        String[] months = Month.getMonthsByBitmap(bitmap);
        String[] expectedResult = new String[] { "January",  null ,"March", null, "May", null, "July", null, "September", null,"November", null};
        Assert.assertArrayEquals(months, expectedResult);
    }

    @Test
    public void getBitmapFromMonths() {
        String[] months = new String[] { "January",  null ,"March", null, "May", null, "July", null, "September", null,"November", null};
        String expectedResult = "101010101010";
        String bitmap = Month.getBitmapFromMonths(months);
        Assert.assertEquals(bitmap, expectedResult);
    }
}