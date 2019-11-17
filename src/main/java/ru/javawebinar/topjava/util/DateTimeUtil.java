package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T> boolean isBetween(T date, T start, T end) {
        if (date instanceof LocalDate) {
            return ((LocalDate) date).compareTo((LocalDate) start) >= 0 &&
                   ((LocalDate) date).compareTo((LocalDate) end) <= 0;
        }
        if (date instanceof LocalTime) {
            return ((LocalTime) date).compareTo((LocalTime) start) >= 0 &&
                   ((LocalTime) date).compareTo((LocalTime) end) <= 0;
        }
        return false;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

