package com.home.webapp.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static LocalDate of(Integer year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
