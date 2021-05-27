package com.rom.util.dateutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hadoop on 2018/3/9.
 *
 * @author hadoop
 */
public class TimeTest {

    public static final SimpleDateFormat FORMAT;

    public static final SimpleDateFormat dateformat1 = new SimpleDateFormat(
            "yyyyMMddHHmmss");


    static {
        FORMAT = new SimpleDateFormat(
                "d/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
    }

    private static Date parseDateFormat(String string) {
        Date parse = null;
        try {
            parse = FORMAT.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    public static void main(String[] args){
        String line = "[30/May/2013:17:38:20 +0800]";

        final int first = line.indexOf("[");
        final int last = line.indexOf("+0800]");
        String time = line.substring(first + 1, last).trim();
        System.out.println(time);
        Date date = parseDateFormat(time);
        String times = dateformat1.format(date);


        System.out.println(times);

    }
}
