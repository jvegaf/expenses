package me.jvegaf.user.infrastructure

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import me.jvegaf.user.domain.*
import me.jvegaf.user.usecase.UserService

@Controller("/api/users")
open class UserController(private val userService: UserService) {
    @Post("/login", consumes = ["application/json"], produces = ["application/json"])
    fun login(@Body request: LoginRequest): LoginResponse {
        val result = userService.loginUser(request.email, request.password)
        return LoginResponse(result?.email ?: "")
    }

    @Post("/register")
    fun register(@Body request: RegisterRequest): RegisterResponse {
        val result = userService.registerUser(request.email, request.password)
        return RegisterResponse(result.email)
    }
}
