package ru.ekbtreeshelp.tests.auth

import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import ru.ekbtreeshelp.tests.RestTest

import static io.restassured.RestAssured.requestSpecification
import static io.restassured.RestAssured.responseSpecification

abstract class AuthTest extends RestTest {
    @BeforeEach
    void configureRestAssured() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(authServiceBaseUri)
                .setPort(authServicePort)
                .setContentType(ContentType.JSON)
                .build()

        responseSpecification = new ResponseSpecBuilder()
                .build()
    }
}
