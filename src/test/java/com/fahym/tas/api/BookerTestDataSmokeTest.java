package com.fahym.tas.api;

import com.fahym.tas.data.api.booker.BookingDataFactory;
import com.fahym.tas.data.api.booker.BookingPatchFactory;
import com.fahym.tas.domain.api.booker.models.Booking;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookerTestDataSmokeTest {

    @Test
    void shouldGenerateValidAndUniqueBookings() {
        Booking b1 = BookingDataFactory.validNewBooking();
        Booking b2 = BookingDataFactory.validNewBooking();

        assertThat(b1.getFirstname()).isNotBlank();
        assertThat(b1.getLastname()).isNotBlank();
        assertThat(b1.getBookingdates()).isNotNull();
        assertThat(b1.getBookingdates().getCheckin()).matches("\\d{4}-\\d{2}-\\d{2}");
        assertThat(b1.getBookingdates().getCheckout()).matches("\\d{4}-\\d{2}-\\d{2}");

        // uniqueness check (lastname includes Unique suffix)
        assertThat(b1.getLastname()).isNotEqualTo(b2.getLastname());
    }

    @Test
    void shouldGenerateValidPatchBodies() {
        var p = BookingPatchFactory.patchFirstname();
        assertThat(p).containsKey("firstname");
        assertThat(p.get("firstname").toString()).startsWith("Patched_");
    }
}