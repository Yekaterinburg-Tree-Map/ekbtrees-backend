package ru.ekbtreeshelp.tests.api

import com.google.common.net.HttpHeaders
import groovy.json.JsonSlurper
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ekbtreeshelp.tests.data.TestContext
import ru.ekbtreeshelp.tests.data.TestUser

import static io.restassured.RestAssured.given
import static org.hamcrest.Matchers.equalTo

class UserControllerTest extends ApiTest {
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
    void testGetCurrentUserInfo() {
        Map<String, Object> userInfo = getCurrentUserInfo()

        assert userInfo.id != null
        assert userInfo.email == testContext.user.email
    }

    @Test
    void testGetUserInfoById() {
        Map<String, Object> userInfo = getCurrentUserInfo()

        get("/api/user/${userInfo.id}")
                .then()
                .statusCode(200)
                .body('id', equalTo(userInfo.id))
    }

    @Test
    void testUpdateUser() {
        Map<String, Object> userInfoBeforeUpdate = getCurrentUserInfo()
        Long userId = userInfoBeforeUpdate.id as Long

        String nameAfterUpdate = 'afterUpdate'
        put("/api/user/${userId}", [firstName: nameAfterUpdate])

        Map<String, Object> userInfoAfterUpdate = getCurrentUserInfo()

        assert nameAfterUpdate == userInfoAfterUpdate.firstName
    }

    @Test
    void testUpdatePassword() {
        String passwordAfterUpdate = 'testPa$$word1'

        put('/api/user/updatePassword', [newPassword: passwordAfterUpdate])
                .then()
                .statusCode(200)

        testContext.user.password = passwordAfterUpdate

        given()
                .spec(new RequestSpecBuilder()
                        .setBaseUri(authServiceBaseUri)
                        .setPort(authServicePort)
                        .setContentType(ContentType.JSON)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Basic ${getBasicAuthHeader()}")
                .post('/auth/login')
        .then()
                .statusCode(200)

    }

    private static Map<String, Object> getCurrentUserInfo() {
        return (new JsonSlurper().parseText(get('/api/user').asString()) as Map<String, Object>)
    }

    @Test
    void testGetListOfUsers(){
        Map<String, Object> userInfo = getCurrentUserInfo()

        Long userId = createUser(new TestUser())
        testContext.user = null

        get("/api/user/listUsersWithoutPaging").then()
                .statusCode(200)
                .body("find { id == ${ userId } }", not(null))
    }

    @Test
    void testBlockUser(){
        Map<String, Object> userInfo = getCurrentUserInfo()
        get("/api/user/block/${userInfo.id}")
                .then()
                .statusCode(200)
                .body("find { id == ${ userInfo.id } }", not(null))

    }

    @Test
    void testGetListOfUsersPaging(){
        Map<String, Object> userInfo = getCurrentUserInfo()

        Long userId = createUser(new TestUser())
        testContext.user = null

        get("/api/user/listUsers/pageNumber=1&pageSize=50").then()
                .statusCode(200)
                .body("find { id == ${ userId } }", not(null))
    }
}
