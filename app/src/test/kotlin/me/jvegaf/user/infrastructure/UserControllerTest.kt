package me.jvegaf.user.infrastructure

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.Test

@MicronautTest
class UserControllerTest {
    @Test
    fun shouldCreateANewUser(spec: RequestSpecification) {
        spec
            .given()
            .contentType("application/json")
            .body("{\"name\":\"paul\"}")
            .`when`()
            .post("/api/users")
            .then()
            .statusCode(200)
    }
}
