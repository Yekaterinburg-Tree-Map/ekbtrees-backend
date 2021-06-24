package ru.ekbtreeshelp.tests.api

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
        sendAddTreeRequest()
                .then()
                .statusCode(201)
    }

    @Test
    void testGetCommentsByTreeId() {
        Long newTreeId = addTree()
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

    private static Response sendAddCommentRequest(Long treeId = null) {
        Long newTreeId = treeId ?: addTree()

        return post('/api/comment/save', [text: 'comment', treeId: newTreeId])
    }

    private static Long addComment(Long treeId = null) {

        Response response = sendAddCommentRequest(treeId)

        response
                .then()
                .statusCode(201)

        return response.asString().toLong()
    }

}
