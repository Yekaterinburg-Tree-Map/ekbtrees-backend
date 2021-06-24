package ru.ekbtreeshelp.tests.api

import org.junit.jupiter.api.Test

import static org.hamcrest.Matchers.*

class HealthCheckControllerTest extends ApiTest {

    @Test
    void testHealthCheck() {
        get('/api/actuator/health')
                .then()
                .statusCode(200)
                .and()
                .body('status', equalTo('UP'))
    }

}
