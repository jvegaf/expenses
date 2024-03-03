package me.jvegaf.user.domain

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class AddUserRequest(
    val name: String,
)

