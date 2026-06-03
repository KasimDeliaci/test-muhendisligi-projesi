package com.testmuh.api;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

class BookingCrudRegressionTest extends BaseApiTest {

    @Test
    @DisplayName("Booking CRUD flow should create, read, patch, delete and verify deletion")
    void bookingCrudFlowShouldWorkSuccessfully() {
        String createBookingRequest = readResource("/payloads/create-booking-request.json");
        String patchBookingRequest = readResource("/payloads/patch-booking-request.json");

        String token = createAuthToken();
        int bookingId = createBooking(createBookingRequest);

        getBooking(bookingId)
                .body("firstname", equalTo("Ali"))
                .body("lastname", equalTo("Yilmaz"))
                .body("totalprice", equalTo(350))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2026-06-10"))
                .body("bookingdates.checkout", equalTo("2026-06-15"))
                .body("additionalneeds", equalTo("Breakfast"));

        patchBooking(bookingId, patchBookingRequest, token)
                .body("firstname", equalTo("Veli"))
                .body("lastname", equalTo("Yilmaz"))
                .body("totalprice", equalTo(350))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2026-06-10"))
                .body("bookingdates.checkout", equalTo("2026-06-15"))
                .body("additionalneeds", equalTo("Lunch"));

        deleteBooking(bookingId, token);
        verifyBookingDeleted(bookingId);
    }

    private int createBooking(String requestBody) {
        return jsonRequest()
                .body(requestBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .time(lessThan(MAX_RESPONSE_TIME_MS))
                .body("bookingid", greaterThan(0))
                .body("booking.firstname", equalTo("Ali"))
                .body("booking.lastname", equalTo("Yilmaz"))
                .body("booking.totalprice", equalTo(350))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2026-06-10"))
                .body("booking.bookingdates.checkout", equalTo("2026-06-15"))
                .body("booking.additionalneeds", equalTo("Breakfast"))
                .extract()
                .path("bookingid");
    }

    private ValidatableResponse getBooking(int bookingId) {
        return jsonRequest()
                .when()
                .get("/booking/{bookingId}", bookingId)
                .then()
                .statusCode(200)
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }

    private String createAuthToken() {
        return jsonRequest()
                .body("{\"username\":\"admin\",\"password\":\"password123\"}")
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .time(lessThan(MAX_RESPONSE_TIME_MS))
                .body("token", not(blankOrNullString()))
                .extract()
                .path("token");
    }

    private ValidatableResponse patchBooking(int bookingId, String requestBody, String token) {
        return jsonRequest()
                .cookie("token", token)
                .body(requestBody)
                .when()
                .patch("/booking/{bookingId}", bookingId)
                .then()
                .statusCode(200)
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }

    private void deleteBooking(int bookingId, String token) {
        jsonRequest()
                .cookie("token", token)
                .when()
                .delete("/booking/{bookingId}", bookingId)
                .then()
                .statusCode(201)
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }

    private void verifyBookingDeleted(int bookingId) {
        jsonRequest()
                .when()
                .get("/booking/{bookingId}", bookingId)
                .then()
                .statusCode(404)
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }
}
