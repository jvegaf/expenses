package me.jvegaf.user.domain

import jakarta.validation.constraints.NotBlank
import me.jvegaf.shared.SortingAndOrderArguments
import org.reactivestreams.Publisher

interface UserRepository {

    fun findById(id: Long): Publisher<User?>

    fun findByEmail(@NotBlank email: String): Publisher<User?>
    fun findAll(args: SortingAndOrderArguments): Publisher<User?>
    fun save(user: User): Publisher<User>
    fun deleteById(id: Long)

}