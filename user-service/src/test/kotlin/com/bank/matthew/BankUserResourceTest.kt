package com.bank.matthew

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class BankBankUserResourceTest {

    @Test
    fun testGetByIdEndpoint() {
        given()
            .param("id", "1")
            .`when`()
            .get("/bankusers")
            .then()
            .statusCode(200)
    }

    @Test
    fun testHelloEndpoint() {
        given()
            .`when`().get("/hello")
            .then()
            .statusCode(200)
            .body(`is`("Hello from RESTEasy Reactive"))
    }

}