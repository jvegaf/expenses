package me.jvegaf.group.domain

typealias GroupBalance = List<UserBalance>


data class UserBalance(
    val name: String,
    val balance: Number
)
