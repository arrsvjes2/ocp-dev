package com.example.finesmart;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/ehlo")
          .then()
             .statusCode(200)
             .body(is("Ehlo employee from employee-pod"));
    }

}