package me.jvegaf.user.infrastructure

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import me.jvegaf.user.domain.AddUserRequest
import me.jvegaf.user.domain.User
import me.jvegaf.user.usecase.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/api/users")
open class UserController(
    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java),
    private val userService: UserService
) {
//    @Post("/login", consumes = ["application/json"], produces = ["application/json"])
//    fun login(@Body request: LoginRequest): LoginResponse {
//        val result = userService.loginUser(request.email, request.password)
//        return LoginResponse(result?.email ?: "")
//    }
//
//    @Post("/register")
//    fun register(@Body request: RegisterRequest): RegisterResponse {
//        val result = userService.registerUser(request.email, request.password)
//        return RegisterResponse(result.email)
//    }

    @Post
    fun create(
        @Body userRequest: AddUserRequest,
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
}
