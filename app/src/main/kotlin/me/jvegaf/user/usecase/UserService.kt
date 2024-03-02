package me.jvegaf.user.usecase

import jakarta.inject.Singleton
import me.jvegaf.user.domain.User
import me.jvegaf.user.domain.UserRepository

@Singleton
class UserService(
    private val userRepository: UserRepository,
) {
    fun registerUser(
        email: String,
        password: String,
    ): User {
        val user = User(null, email, password)
        return userRepository.save(user)
    }

    fun loginUser(
        email: String,
        password: String,
    ): User? {
        return userRepository.findByEmail(email)
            ?.takeIf { it.password == password }
    }

    fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow()
    }
}
