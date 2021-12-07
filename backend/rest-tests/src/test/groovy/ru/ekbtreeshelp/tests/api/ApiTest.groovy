package ru.ekbtreeshelp.tests.api

import groovy.json.JsonSlurper
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.http.ContentType
import io.restassured.response.Response
import org.junit.jupiter.api.BeforeEach
import ru.ekbtreeshelp.tests.RestTest
import ru.ekbtreeshelp.tests.data.SpeciesTreeDto

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

    protected static Response sendCreateTreeRequest(Map<String, Object> creationParamsOverride = [:]) {
        Map<String, Object> body = [age                   : 1,
                                    conditionAssessment   : 1,
                                    diameterOfCrown       : 1,
                                    fileIds               : [],
                                    geographicalPoint     : [
                                            latitude : 55.5,
                                            longitude: 55.5
                                    ],
                                    heightOfTheFirstBranch: 1,
                                    numberOfTreeTrunks    : 1,
                                    speciesId             : 8,
                                    status                : 'alive',
                                    treeHeight            : 1,
                                    treePlantingType      : 'planting type',
                                    trunkGirth            : 1]

        body << creationParamsOverride

        post('/api/tree', body)
    }

    protected static Response sendCreateTreeWithFilesRequest() {
        Map<String, Object> body = [age                   : 1,
                                    conditionAssessment   : 1,
                                    diameterOfCrown       : 1,
                                    fileIds               : [uploadFile()],
                                    geographicalPoint     : [
                                            latitude : 55.5,
                                            longitude: 55.5
                                    ],
                                    heightOfTheFirstBranch: 1,
                                    numberOfTreeTrunks    : 1,
                                    speciesId             : 8,
                                    status                : 'alive',
                                    treeHeight            : 1,
                                    treePlantingType      : 'planting type',
                                    trunkGirth            : 1]

        post('/api/tree', body)
    }

    protected static Long createTree(Map<String, Object> creationParamsOverride = [:]) {
        Response response = sendCreateTreeRequest(creationParamsOverride)

        response.then().statusCode(201)

        return response.asString().toLong()
    }

    protected static Long createTreeWithFiles() {
        Response response = sendCreateTreeWithFilesRequest()

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
