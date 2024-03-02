package me.jvegaf.user.domain

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import me.jvegaf.user.domain.User

@Repository
interface UserRepository: CrudRepository<User, Long>  {
    fun findByEmail(email: String): User?
}