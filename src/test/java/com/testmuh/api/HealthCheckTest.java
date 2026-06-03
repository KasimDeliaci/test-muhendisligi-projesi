package com.testmuh.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

class HealthCheckTest extends BaseApiTest {

    @Test
    @DisplayName("GET /ping should return service health status")
    void pingShouldReturnCreatedStatus() {
        given()
                .when()
                .get("/ping")
                .then()
                .statusCode(201)
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }
}
