package com.fahym.tas.steps.api;

import com.fahym.tas.core.api.restassured.RestAssuredClient;
import com.fahym.tas.core.api.restassured.RestAssuredSpecFactory;
import com.fahym.tas.core.config.Config;
import com.fahym.tas.core.config.ConfigLoader;
import com.fahym.tas.data.api.booker.BookingDataFactory;
import com.fahym.tas.data.api.booker.BookingPatchFactory;
import com.fahym.tas.domain.api.booker.clients.AuthClient;
import com.fahym.tas.domain.api.booker.clients.BookingClient;
import com.fahym.tas.domain.api.booker.flows.BookingFlow;
import com.fahym.tas.domain.api.booker.models.Booking;
import io.cucumber.java.en.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Defines Cucumber step implementations for Booker API CRUD scenarios.
 *
 * This class maps Gherkin steps to executable test logic by configuring the API
 * client, invoking booking business flows, storing scenario state, and performing
 * assertions on the results. It represents the BDD layer and delegates technical
 * API details to lower layers such as BookingFlow and the domain clients.
 *
 * Main interaction:
 *   Gherkin steps -> BookerCrudSteps -> BookingFlow
 */
public class BookerCrudSteps {

    private final Config cfg = ConfigLoader.load();

    private RestAssuredClient ra;
    private BookingFlow flow;

    private String token;
    private int bookingId;

    private Booking createdBooking;
    private Booking putBooking;

    private Booking lastFetched;

    @Given("the API client is configured")
    public void apiClientConfigured() {
        ra = new RestAssuredClient(RestAssuredSpecFactory.baseSpec(cfg));
        flow = new BookingFlow(new AuthClient(ra), new BookingClient(ra));
    }

    @Given("an auth token is available")
    public void authTokenAvailable() {
        token = flow.authToken(BookingDataFactory.defaultUsername(), BookingDataFactory.defaultPassword());
        assertThat(token).isNotBlank();
    }

    @When("the user creates a new booking")
    public void createNewBooking() {
        createdBooking = BookingDataFactory.validNewBooking();
        bookingId = flow.createBookingAndReturnId(createdBooking);
    }

    @Then("the booking should be created successfully")
    public void bookingCreatedSuccessfully() {
        assertThat(bookingId).isPositive();
    }

    @When("the user retrieves the created booking")
    public void retrieveCreatedBooking() {
        lastFetched = flow.getBooking(bookingId);
    }

    @Then("the booking details should be returned")
    public void bookingDetailsShouldBeReturned() {
        assertThat(lastFetched).isNotNull();
        assertThat(lastFetched.getFirstname()).isNotBlank();
        assertThat(lastFetched.getLastname()).isNotBlank();
    }

    @When("the user updates the booking using PUT")
    public void updateBookingUsingPut() {
        putBooking = BookingDataFactory.validPutBooking();
        flow.putUpdateBooking(bookingId, putBooking, token);
    }

    @Then("the booking should be updated successfully")
    public void bookingShouldBeUpdatedSuccessfully() {
    	//bookingId = 11; // FORCE FAIL
        Booking fetched = flow.getBooking(bookingId);
        assertThat(fetched.getFirstname()).isEqualTo(putBooking.getFirstname());
        assertThat(fetched.getLastname()).isEqualTo(putBooking.getLastname());
        assertThat(fetched.getTotalprice()).isEqualTo(putBooking.getTotalprice());
    }

    @When("the user updates the booking using PATCH")
    public void updateBookingUsingPatch() {
        var patch = BookingPatchFactory.patchFirstname();
        flow.patchUpdateBooking(bookingId, patch, token);

        // store expected value for next assertion
        String expectedFirstname = patch.get("firstname").toString();
        // fetch now for the next "Then"
        lastFetched = flow.getBooking(bookingId);

        assertThat(lastFetched.getFirstname()).isEqualTo(expectedFirstname);
    }

    @Then("the booking should reflect the patched fields")
    public void bookingShouldReflectPatchedFields() {
        
        assertThat(lastFetched).isNotNull();
        assertThat(lastFetched.getFirstname()).startsWith("Patched_");
    }

    @When("the user deletes the booking")
    public void deleteBooking() {
        flow.deleteBooking(bookingId, token);
    }

    @Then("the booking should not be retrievable anymore")
    public void bookingShouldNotBeRetrievableAnymore() {
    	
        // We expect GET to fail (404) after delete, but our flow throws on non-200.
        // So we verify by calling client directly and checking status.
        var raw = new BookingClient(ra).getBookingRaw(bookingId);
        assertThat(raw.statusCode()).isEqualTo(404);
       //assertThat(bookingId).isEqualTo(-1); // force test to failed
    }
}