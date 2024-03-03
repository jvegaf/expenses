package me.jvegaf.user.usecase

import jakarta.inject.Singleton
import me.jvegaf.user.domain.User
import me.jvegaf.user.domain.UserRepository

@Singleton
class UserService(
    private val userRepository: UserRepository,
) {

    fun save(user: User): User {
        return userRepository.save(user)
    }

    fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow()
    }

    fun findByName(name: String): User? {
        return userRepository.findByName(name)
    }
}
