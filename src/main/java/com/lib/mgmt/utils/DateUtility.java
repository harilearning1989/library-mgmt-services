package com.lib.mgmt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DateUtility {

    public static Date parseDate(String inputDate) {

        Date outputDate = null;
        List<String> formats = getStringFormates();
        for (String format : formats) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            try {
                outputDate = df.parse(inputDate);
                break;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println("inputDate ==> " + inputDate + ", outputDate ==> " + outputDate);
        return outputDate;
    }

    private static List<String> getStringFormates() {
        List<String> patterns = Arrays.asList("yyyy.MM.dd G 'at' HH:mm:ss z",
                "EEE, MMM d, ''yy",
                "h:mm a",
                "hh 'o''clock' a, zzzz",
                "K:mm a, z",
                "yyyyy.MMMMM.dd GGG hh:mm aaa",
                "EEE, d MMM yyyy HH:mm:ss Z",
                "yyMMddHHmmssZ",
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                "YYYY-'W'ww-u",
                "EEE, dd MMM yyyy HH:mm:ss z",
                "EEE, dd MMM yyyy HH:mm zzzz",
                "yyyy-MM-dd'T'HH:mm:ssZ",
                "yyyy-MM-dd'T'HH:mm:ss.SSSzzzz",
                "yyyy-MM-dd'T'HH:mm:sszzzz",
                "yyyy-MM-dd'T'HH:mm:ss z",
                "yyyy-MM-dd'T'HH:mm:ssz",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm",
                "yyyy-MM-dd'T'HHmmss.SSSz",
                "yyyy-MM-dd",
                "yyyyMMdd",
                "dd/MM/yy",
                "dd/MM/yyyy");
        //2023-03-29T21:47
        return patterns;
    }

    public static void main(String[] args) {
        DateUtility.parseDate("20181118");
        DateUtility.parseDate("2018-11-18");
        DateUtility.parseDate("18/11/18");
        DateUtility.parseDate("18/11/2018");
        DateUtility.parseDate("2018.11.18 AD at 12:08:56 PDT");
        System.out.println("");
        DateUtility.parseDate("Wed, Nov 18, '18");
        DateUtility.parseDate("12:08 PM");
        DateUtility.parseDate("12 o'clock PM, Pacific Daylight Time");
        DateUtility.parseDate("0:08 PM, PDT");
        DateUtility.parseDate("02018.Nov.18 AD 12:08 PM");
        System.out.println("");
        DateUtility.parseDate("Wed, 18 Nov 2018 12:08:56 -0700");
        DateUtility.parseDate("181118120856-0700");
        DateUtility.parseDate("2018-11-18T12:08:56.235-0700");
        DateUtility.parseDate("2018-11-18T12:08:56.235-07:00");
        DateUtility.parseDate("2018-W27-3");
    }

    public static String formatDate(Date date, String requiredDateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(requiredDateFormat);
        String outputDateFormatted = df.format(date);
        return outputDateFormatted;
    }
}
