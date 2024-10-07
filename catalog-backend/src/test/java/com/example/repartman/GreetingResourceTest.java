package com.example.repartman;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/ehlo")
          .then()
             .statusCode(200)
             .body(is("Ehlo catalog from catalog-pod.namespace"));
    }

}