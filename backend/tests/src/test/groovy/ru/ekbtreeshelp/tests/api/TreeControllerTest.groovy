package ru.ekbtreeshelp.tests.api

import groovy.json.JsonSlurper
import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ekbtreeshelp.tests.data.GeographicalPointDto
import ru.ekbtreeshelp.tests.data.SpeciesTreeDto
import ru.ekbtreeshelp.tests.data.TestContext
import ru.ekbtreeshelp.tests.data.TestUser
import ru.ekbtreeshelp.tests.data.TreeDto

import static org.hamcrest.Matchers.*

class TreeControllerTest extends ApiTest {

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
    void testAddTree() {
        addTree()
    }

    @Test
    void testGetCurrentUserTrees() {

        Long newTreeId = addTree()

        get('/api/tree/get')
                .then()
                .statusCode(200)
                .body("find { it.id == ${ newTreeId } }", not(null))
    }

    @Test
    void testGetTreeById() {

        Long newTreeId = addTree()

        get("/api/tree/get/${ newTreeId }")
                .then()
                .statusCode(200)
                .body('id as Long', equalTo(newTreeId))
    }

    @Test
    void testDeleteTreeById() {

        Long newTreeId = addTree()

        delete("/api/tree/delete/${ newTreeId }")
                .then()
                .statusCode(200)
    }

    @Test
    void testAttachFile() {

        Long newTreeId = addTree()

        File file = File.createTempFile('testAttachFile', null)
        file.write('testFileContent')

        post("/api/tree/attachFile/${ newTreeId }", file)
                .then()
                .statusCode(200)
    }

    @Test
    void testUnauthenticatedUserCantAddTree() {
        testContext.user = null

        sendAddTreeRequest().then().statusCode(403)
    }
}
