package com.cqust.exam2.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool {
    public static String getMusicTime(long time){
        int minutes = (int) (time/60);
        int second = (int) (time%60);
        return String.format("%02d:%02d",minutes, second);
    }
}
