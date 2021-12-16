package ru.ekbtreeshelp.tests.api

import groovy.json.JsonSlurper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ekbtreeshelp.tests.data.TestContext
import ru.ekbtreeshelp.tests.data.TestUser

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
