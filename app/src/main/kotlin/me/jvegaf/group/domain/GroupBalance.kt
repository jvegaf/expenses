package me.jvegaf.group.domain

import io.micronaut.serde.annotation.Serdeable

typealias GroupBalance = List<UserBalance>

@Serdeable
data class UserBalance(
    val name: String,
    val balance: Number
)
