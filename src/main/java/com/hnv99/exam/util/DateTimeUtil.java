package com.hnv99.exam.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class DateTimeUtil {

    private DateTimeUtil() {
    }

    private static final String dataFormat = "yyyy-MM-dd";
    private static final String format = "yyyy-MM-dd HH:mm:ss";

    /**
     * Get the current time using the format yyyy-MM-dd HH:mm:ss
     *
     * @return Returns the current time
     */
    public static LocalDateTime getDateTime() {
        return LocalDateTime.parse(datetimeToStr(LocalDateTime.now()), DateTimeFormatter.ofPattern(format));
    }

    /**
     * Get the current date using the format yyyy-MM-dd
     *
     * @return Returns the current date
     */
    public static LocalDate getDate() {
        return LocalDate.parse(dateToStr(LocalDate.now()), DateTimeFormatter.ofPattern(dataFormat));
    }

    /**
     * Convert a string representation of date and time to LocalDateTime format
     *
     * @param dateTime The string to be converted
     * @return Returns the LocalDateTime format
     */
    public static String datetimeToStr(LocalDateTime dateTime) {
        return DateTimeFormatter.ofPattern(format).format(dateTime);
    }

    /**
     * Convert a string representation of date to LocalDate format
     *
     * @param date The string to be converted
     * @return Returns the LocalDate format
     */
    public static String dateToStr(LocalDate date) {
        return DateTimeFormatter.ofPattern(dataFormat).format(date);
    }
}
