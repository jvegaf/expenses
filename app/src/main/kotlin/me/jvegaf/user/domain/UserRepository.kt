package me.jvegaf.user.domain

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByName(name: String): User?
}