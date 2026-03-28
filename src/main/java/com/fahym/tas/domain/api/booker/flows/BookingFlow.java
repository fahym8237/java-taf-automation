package com.fahym.tas.domain.api.booker.flows;

import com.fahym.tas.domain.api.booker.clients.AuthClient;
import com.fahym.tas.domain.api.booker.clients.BookingClient;
import com.fahym.tas.domain.api.booker.models.Booking;
import com.fahym.tas.domain.api.booker.models.CreateBookingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public final class BookingFlow {

    private final AuthClient auth;
    private final BookingClient booking;
    private final ObjectMapper mapper = new ObjectMapper();

    public BookingFlow(AuthClient auth, BookingClient booking) {
        this.auth = auth;
        this.booking = booking;
    }

    public int createBookingAndReturnId(Booking b) {
        var res = booking.createBookingRaw(b);
        if (res.statusCode() != 200) {
            throw new IllegalStateException("Create booking failed. Status=" + res.statusCode() + " Body=" + res.body());
        }
        try {
            CreateBookingResponse cbr = mapper.readValue(res.body(), CreateBookingResponse.class);
            return cbr.getBookingid();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse create booking response. Body=" + res.body(), e);
        }
    }

    public String authToken(String username, String password) {
        return auth.createToken(username, password);
    }

    public void putUpdateBooking(int bookingId, Booking fullBooking, String token) {
        var res = booking.updateBookingPutRaw(bookingId, fullBooking, token);
        if (res.statusCode() != 200) {
            throw new IllegalStateException("PUT update failed. Status=" + res.statusCode() + " Body=" + res.body());
        }
    }

    public void patchUpdateBooking(int bookingId, Map<String, Object> patch, String token) {
        var res = booking.updateBookingPatchRaw(bookingId, patch, token);
        if (res.statusCode() != 200) {
            throw new IllegalStateException("PATCH update failed. Status=" + res.statusCode() + " Body=" + res.body());
        }
    }

    public Booking getBooking(int bookingId) {
        var res = booking.getBookingRaw(bookingId);
        if (res.statusCode() != 200) {
            throw new IllegalStateException("GET booking failed. Status=" + res.statusCode() + " Body=" + res.body());
        }
        try {
            return mapper.readValue(res.body(), Booking.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse booking. Body=" + res.body(), e);
        }
    }

    public void deleteBooking(int bookingId, String token) {
        var res = booking.deleteBookingRaw(bookingId, token);
        // Restful-booker returns 201 on successful delete in many examples
        if (res.statusCode() != 201 && res.statusCode() != 200) {
            throw new IllegalStateException("DELETE booking failed. Status=" + res.statusCode() + " Body=" + res.body());
        }
    }
}