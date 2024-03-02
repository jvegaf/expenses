package me.jvegaf.group.infrastructure
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.Test

@MicronautTest
class GroupControllerTest {

    @Test
    fun shouldCreateANewGroup(spec: RequestSpecification) {
        spec
            .`when`()
            .post("/api/groups")
            .then()
            .statusCode(201)
    }

}
