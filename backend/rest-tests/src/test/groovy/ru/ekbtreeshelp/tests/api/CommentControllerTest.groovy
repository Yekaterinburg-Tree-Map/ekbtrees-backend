package ru.ekbtreeshelp.tests.api

import groovy.json.JsonSlurper
import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ekbtreeshelp.tests.data.TestContext
import ru.ekbtreeshelp.tests.data.TestUser

import static org.hamcrest.Matchers.*

class CommentControllerTest extends ApiTest {

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
    void testAddComment() {
        sendCreateTreeRequest()
                .then()
                .statusCode(201)
    }

    @Test
    void testGetCommentsByTreeId() {
        Long newTreeId = createTree()
        addComment(newTreeId)

        get("/api/comment/get-all/${ newTreeId }")
                .then()
                .statusCode(200)
                .body('find { it.id }', not(null))
    }

    @Test
    void testDeleteComment() {
        Long newCommentId = addComment()

        delete("/api/comment/delete/${ newCommentId }")
                .then()
                .statusCode(200)
    }

    @Test
    void testDeleteAllComments() {
        Map<String, Object> userInfo = getCurrentUserInfo()
        Long newTreeId = createTree()
        addComment(newTreeId)
        addComment(newTreeId)


        delete("/api/comment/delete-all/${userInfo.id}")
                .then()
                .statusCode(200)
    }

    @Test
    void testEditComment() {
        Map<String, Object> userInfo = getCurrentUserInfo()
        Long newTreeId = createTree()
        Long commentId = addComment(newTreeId)

        put("/api/comment/${commentId}", [text: 'newText'])
                .then()
                .statusCode(200)

        get("api/comment/get-all/${ userInfo.id }")
            .then()
            .statusCode(200)
                .body("find { it.text == 'newText' }", not(null))
    }

    @Test
    void testGetAllCommentsByAuthorId() {
        Map<String, Object> userInfo = getCurrentUserInfo()
        Long newTreeId = createTree()
        Long commentId = addComment(newTreeId)

        get("api/comment/get-all/${ userInfo.id }")
                .then()
                .statusCode(200)
                .body("find { it.id == ${ commentId } }", not(null))
    }

    private static Response sendAddCommentRequest(Long treeId = null) {
        Long newTreeId = treeId ?: createTree()

        return post('/api/comment/save', [text: 'comment', treeId: newTreeId])
    }

    private static Map<String, Object> getCurrentUserInfo() {
        return (new JsonSlurper().parseText(get('/api/user').asString()) as Map<String, Object>)
    }

    private static Long addComment(Long treeId = null) {

        Response response = sendAddCommentRequest(treeId)

        response
                .then()
                .statusCode(201)

        return response.asString().toLong()
    }
}
