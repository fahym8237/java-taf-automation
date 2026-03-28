package com.fahym.tas.domain.api.booker.models;

public final class CreateBookingResponse {
    private int bookingid;
    private Booking booking;

    public CreateBookingResponse() {}

    public int getBookingid() { return bookingid; }
    public Booking getBooking() { return booking; }

    public void setBookingid(int bookingid) { this.bookingid = bookingid; }
    public void setBooking(Booking booking) { this.booking = booking; }
}