package com.example.uspForum.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DateHandlerTests {

    @Test
    @DisplayName("isOlderThanOneDay returns false correctly")
    void isOlderThanOneDayReturnsFalseCorrectly() {
        DateHandler dateHandler = new DateHandler();

        Date date = new Date();
        // 86400000 Milliseconds represents exactly one day, so 86399000 is one second away from exactly one day
        date.setTime(date.getTime() - 86399000);

        assertFalse(dateHandler.isOlderThanOneDay(date));
    }

    @Test
    @DisplayName("isOlderThanOneDay returns true correctly when exactly one day has passed")
    void isOlderThanOneDayReturnsTrueCorrectlyWhenExactlyOneDayHasPassed() {
        DateHandler dateHandler = new DateHandler();

        Date date = new Date();
        // 86400000 Milliseconds represents exactly one day
        date.setTime(date.getTime() - 86400000);

        assertTrue(dateHandler.isOlderThanOneDay(date));
    }

    @Test
    @DisplayName("isOlderThanOneDay returns true correctly when more than one day has passed")
    void isOlderThanOneDayReturnsTrueCorrectlyWhenMoreThanOneDayHasPassed() {
        DateHandler dateHandler = new DateHandler();

        Date date = new Date();
        // 86400000 Milliseconds represents exactly one day
        date.setTime(date.getTime() - 86400001);

        assertTrue(dateHandler.isOlderThanOneDay(date));
    }

}
