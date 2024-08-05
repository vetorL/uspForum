package com.example.uspForum.util;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class DateHandler {

    public boolean isOlderThanOneDay(Date dateToCheck) {
        // Convert Date to Instant
        Instant instantToCheck = dateToCheck.toInstant();
        Instant now = Instant.now();

        // Calculate the duration between now and the date to check
        Duration duration = Duration.between(instantToCheck, now);

        // Check if the duration is greater than one day
        return duration.toDays() >= 1;
    }

}
