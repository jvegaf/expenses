package me.jvegaf.expense.domain

import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Serdeable
data class AddExpense(
    val creator: Int,
    val amount: Double,
    val description: String,
    val paymentDate: LocalDateTime
)

