package br.com.nathan.hotel.core.entity;

import java.time.DayOfWeek;
import java.util.Set;

public class Days {

    public static Set<DayOfWeek> workingDays() {
        return Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
    }

    public static Set<DayOfWeek> weekendDays() {
        return Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    }
}
