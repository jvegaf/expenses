package me.jvegaf.group.domain

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class GroupResponse(
    val id: Long,
    val name: String,
)
