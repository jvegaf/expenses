package me.jvegaf.user.domain

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class AddUser(
    val name: String,
)

