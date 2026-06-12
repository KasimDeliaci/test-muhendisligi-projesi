package com.testmuh.api;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

class BaseApiTest {

    protected static final long MAX_RESPONSE_TIME_MS = 5000L;

    @BeforeAll
    static void setUpBaseUri() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.filters(
                new RequestLoggingFilter(LogDetail.ALL),
                new ResponseLoggingFilter(LogDetail.ALL));
    }

    protected RequestSpecification jsonRequest() {
        return given()
                .contentType(JSON)
                .header("Accept", "application/json");
    }

    protected String readResource(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + path);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read resource: " + path, exception);
        }
    }
}
