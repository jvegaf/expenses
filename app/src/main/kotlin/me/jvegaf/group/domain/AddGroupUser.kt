package me.jvegaf.group.domain

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class AddGroupUser (
    var userId: Int
)