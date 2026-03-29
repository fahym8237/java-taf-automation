package com.fahym.tas.domain.api.booker.models;
/**
 * Represents the response returned after a booking is created.
 *
 * This model contains the generated booking identifier and the created booking
 * details returned by the create-booking endpoint. It is deserialized by
 * BookingFlow to extract the booking ID and optionally inspect the created data.
 *
 * Main interaction:
 *   API response -> CreateBookingResponse -> BookingFlow
 */

public final class CreateBookingResponse {
    private int bookingid;
    private Booking booking;

    public CreateBookingResponse() {}

    public int getBookingid() { return bookingid; }
    public Booking getBooking() { return booking; }

    public void setBookingid(int bookingid) { this.bookingid = bookingid; }
    public void setBooking(Booking booking) { this.booking = booking; }
}