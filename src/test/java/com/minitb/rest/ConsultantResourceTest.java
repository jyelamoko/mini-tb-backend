package com.minitb.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class ConsultantResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/api/consultants")
          .then()
             .statusCode(200);
    }

    @Test
    public void testCreateConsultant() {
        given()
                .header("Content-Type", "application/json")
                .body("{\"firstName\":\"Durand\",\"lastName\":\"Alice\",\"jobTitle\":\"Développeur\"}")
                .when().post("/api/consultants")
                .then()
                .statusCode(200);
    }

}