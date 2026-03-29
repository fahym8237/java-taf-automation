package com.fahym.tas.domain.api.booker.models;

/**
 * Represents the date range portion of a booking entity.
 *
 * This model stores the check-in and check-out dates as a nested component
 * inside the Booking model, matching the JSON structure expected by the
 * booker API for booking date information.
 *
 * Main interaction:
 *   Booking -> BookingDates
 */
public final class BookingDates {
    private String checkin;
    private String checkout;

    public BookingDates() {}

    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public String getCheckin() { return checkin; }
    public String getCheckout() { return checkout; }

    public void setCheckin(String checkin) { this.checkin = checkin; }
    public void setCheckout(String checkout) { this.checkout = checkout; }
}