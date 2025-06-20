package com.example.omcserver.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utiles {
    public static String getFormatTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        String format = localDateTime.format(formatter);
        return format;
    }
}
