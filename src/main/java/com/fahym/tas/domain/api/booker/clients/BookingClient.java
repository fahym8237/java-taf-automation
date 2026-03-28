package com.fahym.tas.domain.api.booker.clients;

import com.fahym.tas.core.api.model.ApiRequest;
import com.fahym.tas.core.api.model.ApiResponse;
import com.fahym.tas.core.api.restassured.RestAssuredClient;
import com.fahym.tas.domain.api.booker.config.BookerPaths;
import com.fahym.tas.domain.api.booker.models.Booking;

public final class BookingClient {

    private final RestAssuredClient client;

    public BookingClient(RestAssuredClient client) {
        this.client = client;
    }

    public ApiResponse createBookingRaw(Booking booking) {
        ApiRequest req = ApiRequest.builder(BookerPaths.BOOKING)
                .header("Content-Type", "application/json")
                .body(booking)
                .build();
        return client.post(req);
    }

    public ApiResponse getBookingRaw(int bookingId) {
        ApiRequest req = ApiRequest.builder(BookerPaths.bookingById(bookingId))
                .build();
        return client.get(req);
    }

    public ApiResponse updateBookingPutRaw(int bookingId, Booking booking, String token) {
        ApiRequest req = ApiRequest.builder(BookerPaths.bookingById(bookingId))
                .header("Content-Type", "application/json")
                // Restful-booker commonly accepts token via Cookie: token=<token>
                .header("Cookie", "token=" + token)
                .body(booking)
                .build();
        return client.put(req);
    }

    public ApiResponse updateBookingPatchRaw(int bookingId, Object patchBody, String token) {
        ApiRequest req = ApiRequest.builder(BookerPaths.bookingById(bookingId))
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .body(patchBody)
                .build();
        return client.patch(req);
    }

    public ApiResponse deleteBookingRaw(int bookingId, String token) {
        ApiRequest req = ApiRequest.builder(BookerPaths.bookingById(bookingId))
                .header("Cookie", "token=" + token)
                .build();
        return client.delete(req);
    }
}