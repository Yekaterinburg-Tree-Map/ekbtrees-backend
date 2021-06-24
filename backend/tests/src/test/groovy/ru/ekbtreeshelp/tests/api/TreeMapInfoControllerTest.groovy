package ru.ekbtreeshelp.tests.api


import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ekbtreeshelp.tests.data.TestContext
import ru.ekbtreeshelp.tests.data.TestUser

import static org.hamcrest.Matchers.*

class TreeMapInfoControllerTest extends ApiTest {

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
    void testGetInRegion() {
        Long newTreeId = addTree()

        get('api/tree-map-info/get-in-region', [x1: 55, y1: 55, x2: 56, y2: 56])
                .then()
                .statusCode(200)
                .body("find { it.id == ${ newTreeId } }", not(null))
    }

    @Test
    void testGetCurrentUserTrees() {
        sendGetUserTreesRequest()
                .then()
                .statusCode(200)
                .body('find { it.id }', is(null))

        Long newTreeId = addTree()

        sendGetUserTreesRequest()
                .then()
                .statusCode(200)
                .body("find { it.id == ${ newTreeId } }", not(null))
    }

    private static Response sendGetUserTreesRequest() {
        return get('/api/tree/get')
    }
}
