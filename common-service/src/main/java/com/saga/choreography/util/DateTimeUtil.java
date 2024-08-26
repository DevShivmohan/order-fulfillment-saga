package com.saga.choreography.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_ss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getDateTime() {
        return LocalDateTime.now().format(dateTimeFormatter_yyyy_MM_dd_HH_mm_ss);
    }
}
