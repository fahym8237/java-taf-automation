package com.fahym.tas.api;

import com.fahym.tas.core.api.restassured.RestAssuredClient;
import com.fahym.tas.core.api.restassured.RestAssuredSpecFactory;
import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.domain.api.booker.clients.AuthClient;
import com.fahym.tas.domain.api.booker.clients.BookingClient;
import com.fahym.tas.domain.api.booker.flows.BookingFlow;
import com.fahym.tas.domain.api.booker.models.Booking;
import com.fahym.tas.domain.api.booker.models.BookingDates;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiLayerSmokeTest {

    @Test
    void shouldCreatePutPatchGetDeleteBooking() {
        Config cfg = ConfigLoader.load();

        RestAssuredClient ra = new RestAssuredClient(RestAssuredSpecFactory.baseSpec(cfg));
        AuthClient authClient = new AuthClient(ra);
        BookingClient bookingClient = new BookingClient(ra);
        BookingFlow flow = new BookingFlow(authClient, bookingClient);

        // Auth (Restful-booker default creds in public examples)
        String token = flow.authToken("admin", "password123");
        assertThat(token).isNotBlank();

        // Create booking
        Booking booking = new Booking(
                "Fahum",
                "TAS",
                111,
                true,
                new BookingDates("2026-02-10", "2026-02-12"),
                "Breakfast"
        );

        int bookingId = flow.createBookingAndReturnId(booking);
        assertThat(bookingId).isPositive();

        // PUT update
        Booking putBooking = new Booking(
                "Fahum",
                "Updated",
                222,
                false,
                new BookingDates("2026-03-01", "2026-03-05"),
                "Lunch"
        );
        flow.putUpdateBooking(bookingId, putBooking, token);

        // PATCH update (partial)
        flow.patchUpdateBooking(bookingId, Map.of("firstname", "Patched"), token);

        // GET and validate
        Booking fetched = flow.getBooking(bookingId);
        assertThat(fetched.getFirstname()).isEqualTo("Patched");
        assertThat(fetched.getLastname()).isEqualTo("Updated");

        // DELETE
        flow.deleteBooking(bookingId, token);
    }
}