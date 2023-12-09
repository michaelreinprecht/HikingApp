package models;

import java.util.Arrays;

public class Month {

    public static final String[] ALL_MONTHS = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    public static String[] getMonthsByBitmap(String bitmap) {
        if (bitmap != null) {
            String[] result = new String[bitmap.length()];
            for (int i = 0; i < bitmap.length(); i++) {
                int index = i % ALL_MONTHS.length;
                if (bitmap.charAt(i) == '1') {
                    result[i] = ALL_MONTHS[index];
                }
            }
            return result;
        } else return null;
    }

    public static String getBitmapFromMonths(String[] months) {
        if (months != null && months.length > 0) {
            char[] result = new char[ALL_MONTHS.length];
            Arrays.fill(result, '0');
            for (String month : months) {
                int index = Arrays.asList(ALL_MONTHS).indexOf(month);
                if (index != -1) {
                    result[index] = '1';
                }
            }
            return new String(result);
        } else return null;
    }
}