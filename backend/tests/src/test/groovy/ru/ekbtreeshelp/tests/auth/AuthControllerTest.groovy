package ru.ekbtreeshelp.tests.auth

import com.google.common.net.HttpHeaders
import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ekbtreeshelp.tests.constants.CookieNames
import ru.ekbtreeshelp.tests.data.TestContext
import ru.ekbtreeshelp.tests.data.TestUser

import static io.restassured.RestAssured.*
import static org.hamcrest.Matchers.*

class AuthControllerTest extends AuthTest {
    @BeforeEach
    void initContext() {
        String email = "${UUID.randomUUID()}@test.mail"
        TestUser user = new TestUser(
                email: email,
                password: 'volunteer'
        )

        createUser(user)

        testContext = new TestContext(
                user: user
        )
    }

    @Test
    void testNewTokens() {
        Response response = given()
                .header(HttpHeaders.AUTHORIZATION, "Basic ${getBasicAuthHeader()}")
                .post('/auth/login')

        response
                .then()
                .statusCode(302)

        testContext.user = null

        given()
                .cookie(CookieNames.REFRESH_TOKEN, response.cookie(CookieNames.REFRESH_TOKEN))
                .post('/auth/newTokens')
                .then()
                .statusCode(302)
                .cookie(CookieNames.ACCESS_TOKEN, not(null))
                .cookie(CookieNames.REFRESH_TOKEN, not(null))
    }
}
