package ru.iskander.tabaev.coworking.services.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateUtils {

    public static boolean checkDateInterval(LocalDateTime beginDate, LocalDateTime endDate) {

        if (beginDate.isAfter(endDate)){
            return false;
        }
        if (beginDate.getMinute() % 30 != 0 && endDate.getMinute() % 30 != 0) {
            return false;
        } else {
            Duration duration = Duration.between(beginDate, endDate);
            long minutes = duration.toMinutes();
            return (minutes % 30 == 0
                    && minutes >= 30);
        }
    }
}
