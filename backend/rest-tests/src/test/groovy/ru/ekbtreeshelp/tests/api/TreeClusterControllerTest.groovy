package ru.ekbtreeshelp.tests.api

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ekbtreeshelp.tests.data.TestContext
import ru.ekbtreeshelp.tests.data.TestUser

import static org.hamcrest.Matchers.*

class TreeClusterControllerTest extends ApiTest {

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
        createTree()

        get('/api/trees-cluster/get-in-region', [x1: 55, y1: 55, x2: 56, y2: 56])
                .then()
                .statusCode(200)
                .body('every { it.count != null && it.count != 0 }', is(true))
                .body('every { it.centre != null }', is(true))
    }
}
