package me.jvegaf.user.domain

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class RegisterRequest(
  val email: String,
  val password: String
)

@Serdeable
data class RegisterResponse(
  val email: String
)
