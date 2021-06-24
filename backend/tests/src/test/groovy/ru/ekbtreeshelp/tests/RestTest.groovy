package ru.ekbtreeshelp.tests

import com.google.common.net.HttpHeaders
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.apache.commons.codec.binary.Base64
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import ru.ekbtreeshelp.tests.constants.CookieNames
import ru.ekbtreeshelp.tests.data.TestContext
import ru.ekbtreeshelp.tests.data.TestUser

import java.nio.charset.StandardCharsets

import static io.restassured.RestAssured.given

@Tag("integration")
abstract class RestTest {

    protected static TestContext testContext
    protected static String authServiceBaseUri
    protected static int authServicePort
    protected static String apiServiceBaseUri
    protected static int apiServicePort

    private static final Map<TestUser, String> USER_TOKENS = [:]

    @BeforeEach
    protected void configureURIs()
    {
        String envApiUri = System.getenv("API_BASE_URI")
        String envApiPort = System.getenv("API_PORT")
        String envAuthUri = System.getenv("AUTH_BASE_URI")
        String envAuthPort = System.getenv("AUTH_PORT")

        apiServiceBaseUri = envApiUri ?: "http://localhost"
        apiServicePort = envApiPort != null ? Integer.parseInt(envApiPort) : 8080
        authServiceBaseUri = envAuthUri ?: "http://localhost"
        authServicePort = envAuthPort != null ? Integer.parseInt(envAuthPort) : 8081
    }

    protected static Response get(String path) {
        return get(path, Map.of())
    }

    protected static Response get(String path, Map<String, Object> queryParams) {

        RequestSpecification specification = given()
                .queryParams(queryParams)

        setAuth(specification)

        return specification
                .when()
                .get(path)
    }

    protected static Response post(String path, Object body) {

        RequestSpecification specification = given()

        if (body instanceof File) {
            specification
                    .multiPart('file', body)
                    .contentType(ContentType.MULTIPART)
        } else {
            specification.body(body)
        }

        setAuth(specification)

        return specification
                .when()
                .post(path)
    }

    protected static Response delete(String path) {

        RequestSpecification specification = given()

        setAuth(specification)

        return specification
                .when()
                .delete(path)
    }

    protected static void createUser(TestUser user) {
        given()
                .baseUri("$authServiceBaseUri/auth/register")
                .port(authServicePort)
                .body([email    : user.email,
                       firstName: user.email,
                       lastName : user.email,
                       password : user.password])
                .post()
                .then()
                .statusCode(200)
    }

    private static void setAuth(RequestSpecification specification) {
        if (testContext?.user) {
            specification.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
        }
    }

    private static String getAccessToken() {
        return USER_TOKENS.computeIfAbsent(
                testContext.user,
                { user ->
                    def response = given()
                            .baseUri(authServiceBaseUri)
                            .port(authServicePort)
                            .header(HttpHeaders.AUTHORIZATION, "Basic " + getBasicAuthHeader())
                            .post("/auth/login")

                    response.then().statusCode(302)

                    return response.cookie(CookieNames.ACCESS_TOKEN)
        })
    }

    protected static String getBasicAuthHeader() {
        TestUser testUser = testContext.user
        return Base64.encodeBase64String("${ testUser.getEmail() }:${ testUser.getPassword() }".getBytes(StandardCharsets.UTF_8))
    }
}
