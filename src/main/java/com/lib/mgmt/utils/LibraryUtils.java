package com.lib.mgmt.utils;

import com.lib.mgmt.models.library.Book;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class LibraryUtils {

    private static final Logger logger = LoggerFactory.getLogger(LibraryUtils.class);

    public static int getRandomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    public static String getSubString(String value, int max) {
        if (StringUtils.isNotEmpty(value)) {
            return value.substring(0, Math.min(value.length(), max));
        }
        return null;
    }

    public static String listOfStringToString(List<String> list) {
        return StringUtils.join(list, ", ");
    }

    public static String stringToDate(String publishDate) {
        //2009-04-01T00:00:00.000-0700
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
        Date date = null;
        try {
            date = simpleDateFormat.parse(publishDate);
            //System.out.println(date);
        } catch (ParseException e) {
            logger.info("stringToDate publishDate::" + publishDate);
            //Sat Mar 25 22:52:53 IST 2023
            simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z uuuu", Locale.ENGLISH);
            try {
                date = simpleDateFormat.parse(publishDate);
                logger.info("stringToDate date::" + date);
                logger.info("=====================");
                /*
                String dateStr = "Mon Jun 18 00:00:00 IST 2012";
                DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss z uuuu", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(dateStr, inputFormat);
                String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
                System.out.println(formattedDate);

                 */
            } catch (ParseException ex) {
                e.printStackTrace();
            }
        }
        if (date != null) {
            String pattern = "MM/dd/yyyy";
            DateFormat df = new SimpleDateFormat(pattern);
            String todayAsString = df.format(date);
            return todayAsString;
        }
        return null;
    }

    public static List<Book> processBooks(List<Book> bookList) {
        return Optional.ofNullable(bookList)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(m -> {
                    m.setPublishedDate(LibraryUtils.stringToDate(m.getPublishedDate()));
                    return m;
                }).collect(toList());
    }

    public static String dateToString(Date issuedDate) {
        //DateFormat dateFormat = new SimpleDateFormat("dd:mm:YYYY hh:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd:mm:YYYY hh:mm:ss aa");
        return dateFormat.format(issuedDate);
    }

    public static Date stringToDateMultipleFormats(String publishDate) {
        List<SimpleDateFormat> patterns = getDateFormats();
        for (SimpleDateFormat pattern : patterns) {
            try {
                //return new Date(pattern.parse(publishDate).getTime());
                return pattern.parse(publishDate);
            } catch (ParseException pe) {
                // Loop on
            }
        }
        return new Date();
    }

    public static List<SimpleDateFormat> getDateFormats() {
        List<SimpleDateFormat> patterns = new ArrayList<>();
        patterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        patterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss'Z'"));
        patterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        patterns.add(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"));
        patterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"));
        return patterns;
    }
}
