package ru.ekbtreeshelp.tests.api

import groovy.json.JsonSlurper
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.http.ContentType
import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import ru.ekbtreeshelp.tests.RestTest
import ru.ekbtreeshelp.tests.data.GeographicalPointDto
import ru.ekbtreeshelp.tests.data.SpeciesTreeDto
import ru.ekbtreeshelp.tests.data.TreeDto

import static io.restassured.RestAssured.requestSpecification
import static io.restassured.RestAssured.responseSpecification

abstract class ApiTest extends RestTest {
    @BeforeEach
    void configureRestAssured() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(apiServiceBaseUri)
                .setPort(apiServicePort)
                .setContentType(ContentType.JSON)
                .build()

        responseSpecification = new ResponseSpecBuilder()
                .build()
    }

    protected static SpeciesTreeDto getFirstSpecie() {
        return (new JsonSlurper().parseText(
                get('/api/species/get-all').asString()
        ) as Collection<SpeciesTreeDto>)[0]
    }

    protected static Response sendAddTreeRequest() {
        TreeDto tree = new TreeDto(
                geographicalPoint: new GeographicalPointDto(
                        longitude: 55.5,
                        latitude: 55.5
                ),
                species: getFirstSpecie(),
                treeHeight: 5.5,
                numberOfTreeTrunks: 1,
                trunkGirth: 5,
                diameterOfCrown: 5,
                heightOfTheFirstBranch: 5,
                conditionAssessment: 5,
                age: 5,
                treePlantingType: 'test',
                status: 'test'
        )

        return post('/api/tree/save', tree)
    }

    protected static Long addTree() {
        Response response = sendAddTreeRequest()

        response.then().statusCode(201)

        return response.asString().toLong()
    }

    protected static Response sendUploadFileRequest() {
        File file = File.createTempFile('testAttachFile', null)
        file.write('testFileContent')

        return post('/api/file/upload', file)
    }

    protected static Long uploadFile() {
        return sendUploadFileRequest().asString().toLong()
    }
}
