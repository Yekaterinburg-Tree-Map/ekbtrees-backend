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
    void testUnauthenticatedUserCanViewTree() {

        Long newTreeId = createTree()

        testContext.user = null

        get("/api/tree/get/${ newTreeId }")
                .then()
                .statusCode(200)
                .body('id as Long', equalTo(newTreeId))
    }

    @Test
    void testAddTreeWithFiles() {
        Long newTreeId = createTreeWithFiles()

        get("/api/tree/get/${ newTreeId }")
                .then()
                .statusCode(200)
    }

    @Test
    void testTreeCreationAndModificationDates() {
        long timeBeforeCreation = System.currentTimeMillis()

        Long newTreeId = createTree()

        get("/api/tree/get/${ newTreeId }")
                .then()
                .statusCode(200)
                .body('created as Long', greaterThan(timeBeforeCreation))
                .body('updated as Long', greaterThan(timeBeforeCreation))
    }

    @Test
    void testEditTree() {
        int initialAge = 2
        int initialCondAssessment = 3

        Long newTreeId = createTree([age: initialAge, conditionAssessment: initialCondAssessment])

        get("/api/tree/get/${ newTreeId }")
                .then()
                .statusCode(200)
                .body('age as int', equalTo(initialAge))
                .body('conditionAssessment as int', equalTo(initialCondAssessment))

        int ageAfterUpdate = 1

        put("/api/tree/${ newTreeId }", [age: ageAfterUpdate])
                .then()
                .statusCode(200)


        get("/api/tree/get/${ newTreeId }")
                .then()
                .statusCode(200)
                .body('age as int', equalTo(ageAfterUpdate))
                .body('conditionAssessment as int', equalTo(initialCondAssessment))
    }

    @Test
    void testCreationParamsValidation() {
        sendCreateTreeRequest([age: -1])
                .then()
                .statusCode(400)
    }

    @Test
    void testIsEditableAndIsDeletableFlags() {
        Long newTreeId = createTree()

        get("/api/tree/get/${ newTreeId }")
                .then()
                .statusCode(200)
                .body('editable', is(true))
                .body('deletable', is(true))

        testContext.user = null

        get("/api/tree/get/${ newTreeId }")
                .then()
                .statusCode(200)
                .body('editable', is(false))
                .body('deletable', is(false))
    }

    @Test
    void testFileIDsReturnsWithTree() {
        Long newTreeId = createTree()

        File file = File.createTempFile('testAttachFile', null)
        file.write('testFileContent')

        Long createdFileId = post("/api/tree/attachFile/${ newTreeId }", file)
                .then()
                .statusCode(200)
                .extract()
                .asString()
                .toLong()

        get("/api/tree/get/${ newTreeId }")
                .then()
                .statusCode(200)
                .body('fileIds?.collect { it as Long }', equalTo([createdFileId]))
    }

    @Test
    void testSpeciesIsUpdatable() {
        Long newTreeId = createTree()

        Long newSpeciesId = 1
        put("/api/tree/${ newTreeId }", [speciesId: newSpeciesId])
                .then()
                .statusCode(200)

        get("/api/tree/get/${ newTreeId }")
                .then()
                .statusCode(200)
                .body('species?.id as Long', equalTo(newSpeciesId))
    }

    @Test
    void testGetAllTrees() {
        var treeId = createTree()
        testContext.user = null

        get("/api/tree/getAll").then()
                .statusCode(200)
                .body("find { it.id == ${ treeId } }", not(null))
    }

    @Test
    void testGetAllTreesByAuthorId() {
        Map<String, Object> userInfo = getCurrentUserInfo()
        var treeId = createTree()
        testContext.user = null

        get("/api/tree/getAllByAuthorId/${ userInfo.id }")
                .then()
                .statusCode(200)
                .body("find { it.id == ${ treeId } }", not(null))
    }

    private static Map<String, Object> getCurrentUserInfo() {
        return (new JsonSlurper().parseText(get('/api/user').asString()) as Map<String, Object>)
    }

    @Test
    void testWrongTreeIdThrows404() {
        get("/api/tree/get/9876543210")
                .then()
                .statusCode(404)
    }

    @Test
    void testCreateTreeWithOnlyRequiredParams() {
        Map<String, Object> body = [age                   : null,
                                    conditionAssessment   : null,
                                    diameterOfCrown       : null,
                                    fileIds               : [],
                                    geographicalPoint     : [
                                            latitude : 55.5,
                                            longitude: 55.5
                                    ],
                                    heightOfTheFirstBranch: null,
                                    numberOfTreeTrunks    : null,
                                    speciesId             : null,
                                    status                : null,
                                    treeHeight            : null,
                                    treePlantingType      : null,
                                    trunkGirth            : null]

        sendCreateTreeRequest(body)
                .then()
                .statusCode(201)
    }
}
