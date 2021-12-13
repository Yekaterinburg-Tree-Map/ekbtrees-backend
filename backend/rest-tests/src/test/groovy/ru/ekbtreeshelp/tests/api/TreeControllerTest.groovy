package ru.ekbtreeshelp.tests.api

import groovy.json.JsonSlurper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ekbtreeshelp.tests.data.TestContext
import ru.ekbtreeshelp.tests.data.TestUser

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
    void testOldAddTree() {
        sendAddTreeRequest()
                .then()
                .statusCode(201)
    }

    @Test
    void testCreateTree() {
        sendCreateTreeRequest()
                .then()
                .statusCode(201)
    }

    @Test
    void testGetCurrentUserTrees() {

        Long newTreeId = createTree()

        get('/api/tree/get')
                .then()
                .statusCode(200)
                .body("find { it.id == ${ newTreeId } }", not(null))
    }

    @Test
    void testGetTreeById() {

        Long newTreeId = createTree()

        get("/api/tree/get/${ newTreeId }")
                .then()
                .statusCode(200)
                .body('id as Long', equalTo(newTreeId))
    }

    @Test
    void testDeleteTreeById() {

        Long newTreeId = createTree()

        delete("/api/tree/delete/${ newTreeId }")
                .then()
                .statusCode(200)
    }

    @Test
    void testAttachFile() {

        Long newTreeId = createTree()

        File file = File.createTempFile('testAttachFile', null)
        file.write('testFileContent')

        post("/api/tree/attachFile/${ newTreeId }", file)
                .then()
                .statusCode(200)
    }

    @Test
    void testUnauthenticatedUserCantAddTree() {
        testContext.user = null

        sendCreateTreeRequest().then().statusCode(403)
    }

    @Test
    void testGetAllTrees() {
        get("/api/tree/getAll").then()
                .statusCode(200)
                .body(not(null))
    }

    @Test
    void testGetAllTreesByAuthorId() {
        Map<String, Object> userInfo = getCurrentUserInfo()
        testContext.user = null
        var treeId = createTree()

        get("/api/tree/getAllByAuthorId/${ userInfo.id }")
                .then()
                .statusCode(200)
                .body("find { it.id == ${ treeId } }", not(null))
    }

    private static Map<String, Object> getCurrentUserInfo() {
        return (new JsonSlurper().parseText(get('/api/user').asString()) as Map<String, Object>)
    }
}
