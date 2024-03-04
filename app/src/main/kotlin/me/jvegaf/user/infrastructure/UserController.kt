package me.jvegaf.user.infrastructure

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import me.jvegaf.user.domain.AddUser
import me.jvegaf.user.domain.User
import me.jvegaf.user.usecase.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/users")
open class UserController(
    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java),
    private val userService: UserService
) {

    @Post
    fun create(
        @Body userRequest: AddUser,
    ): User {
        val user = User(name = userRequest.name)
        logger.info("User created: {}", user)
        return userService.save(user)
    }

    @Get("/{name}")
    fun findByName(name: String): User? {
        logger.info("User findByName: {}", name)
        return userService.findByName(name)
    }

    @Get
    fun findAll(): List<User> {
        logger.info("User findAll")
        return userService.findAll()
    }

    @Get("/{id}")
    fun findById(id: Int): Optional<User> {
        logger.info("User findById: {}", id)
        return userService.findById(id)
    }
}
