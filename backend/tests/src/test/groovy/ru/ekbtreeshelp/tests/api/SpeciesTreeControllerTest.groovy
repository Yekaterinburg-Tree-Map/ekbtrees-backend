package ru.ekbtreeshelp.tests.api


import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ekbtreeshelp.tests.constants.Users
import ru.ekbtreeshelp.tests.data.TestContext
import ru.ekbtreeshelp.tests.data.TestUser

import static org.hamcrest.Matchers.*

class SpeciesTreeControllerTest extends ApiTest {

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
    void testGetAll() {
        get('/api/species/get-all')
                .then()
                .statusCode(200)
                .body('findAll { it.title }', not(empty()))
    }

    @Test
    void testVolunteerCantCreateSpecies() {
        post('/api/species/save', [title: 'testSpecies'])
                .then()
                .statusCode(403)
    }

    @Test
    void testSuperUserCanCreateSpecies() {
        testContext.user = Users.SUPER_USER

        post('/api/species/save', [title: 'testSpecies'])
                .then()
                .statusCode(201)
                .body(not(emptyString()))
    }

    @Test
    void testSuperUserCanEditSpecies() {
        testContext.user = Users.SUPER_USER

        Long newSpeciesId = post('/api/species/save', [title: 'testSpeciesToEdit']).asString().toLong()

        post('/api/species/save', [id: newSpeciesId, title: 'testSpecies'])
                .then()
                .statusCode(201)
    }

    @Test
    void testSuperUserCanDeleteSpecies() {
        testContext.user = Users.SUPER_USER

        Long newSpeciesId = post('/api/species/save', [title: 'testSpeciesToDelete']).asString().toLong()

        delete("/api/species/delete/${newSpeciesId}")
                .then()
                .statusCode(200)
    }

}
