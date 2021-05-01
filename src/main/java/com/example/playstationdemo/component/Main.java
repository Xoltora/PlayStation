package com.example.playstationdemo.component;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Main {
    public static void main(String[] args) {
        LocalDateTime time1 = LocalDateTime.now();

        LocalDateTime time2 = time1.plusHours(2).plusMinutes(20).plusSeconds(30);

        Long t1 = time1.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        Long t2 = time2.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        Long t = (t2 - t1) / 1000;

        System.out.println(t / 3600.0 + " " + t / 60 + " " + t % 60);
    }
}
