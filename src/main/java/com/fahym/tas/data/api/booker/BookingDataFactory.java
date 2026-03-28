package com.fahym.tas.data.api.booker;

import com.fahym.tas.data.generation.Randoms;
import com.fahym.tas.data.generation.Unique;
import com.fahym.tas.domain.api.booker.models.Booking;
import com.fahym.tas.domain.api.booker.models.BookingDates;

import java.time.LocalDate;

public final class BookingDataFactory {
    private BookingDataFactory() {}

    /** Valid booking for POST /booking */
    public static Booking validNewBooking() {
        String first = "Api_" + Randoms.alphanum(5);
        String last = "User_" + Unique.suffix();

        return new Booking(
                first,
                last,
                100 + (int)(Math.random() * 900),     // 100..999
                true,
                nextNDaysDates(5, 7),
                "Breakfast"
        );
    }

    /** Valid booking for PUT /booking/{id} (full replacement) */
    public static Booking validPutBooking() {
        String first = "Put_" + Randoms.alphanum(5);
        String last = "Updated_" + Unique.suffix();

        return new Booking(
                first,
                last,
                200 + (int)(Math.random() * 800),     // 200..999
                false,
                nextNDaysDates(10, 15),
                "Lunch"
        );
    }

    /** Utility for stable ISO dates */
    public static BookingDates nextNDaysDates(int checkinPlusDays, int checkoutPlusDays) {
        if (checkoutPlusDays <= checkinPlusDays) {
            throw new IllegalArgumentException("checkoutPlusDays must be > checkinPlusDays");
        }

        LocalDate today = LocalDate.now();
        String checkin = today.plusDays(checkinPlusDays).toString();    // yyyy-MM-dd
        String checkout = today.plusDays(checkoutPlusDays).toString();

        return new BookingDates(checkin, checkout);
    }

    /** Common auth credentials for Restful-Booker demo */
    public static String defaultUsername() {
        return "admin";
    }

    public static String defaultPassword() {
        return "password123";
    }
}