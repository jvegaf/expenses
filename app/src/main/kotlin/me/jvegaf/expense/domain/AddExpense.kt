package me.jvegaf.expense.domain

import io.micronaut.serde.annotation.Serdeable
import java.util.*

@Serdeable
data class AddExpenseRequest(
    val creator: Long,
    val amount: Double,
    val description: String,
    val paymentDate: Date
)

