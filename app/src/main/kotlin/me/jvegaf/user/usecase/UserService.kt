package me.jvegaf.user.usecase

import io.micronaut.core.annotation.NonNull
import jakarta.inject.Singleton
import me.jvegaf.user.domain.User
import me.jvegaf.user.domain.UserRepository
import java.util.Optional

@Singleton
class UserService(
    private val userRepository: UserRepository,
) {

    fun save(user: User): User {
        return userRepository.save(user)
    }

    fun findById(id: Int): @NonNull Optional<User>? {
        return userRepository.findById(id)
    }

    fun findByName(name: String): User? {
        return userRepository.findByName(name)
    }
}
