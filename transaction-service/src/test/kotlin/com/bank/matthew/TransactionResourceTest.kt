package com.bank.matthew

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test

@QuarkusTest
class TransactionResourceTest {

    @Test
    fun testGetByIdEndpoint() {
        given()
            .param("id", "1")
            .`when`()
            .get("/bankusers")
            .then()
            .statusCode(200)
    }

}