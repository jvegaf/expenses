package me.jvegaf.expense.domain

import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.*
import me.jvegaf.group.domain.Group
import me.jvegaf.user.domain.User
import java.util.Date

@Entity
data class Expense(
    @Id
    @GeneratedValue
    var id: Long?,
    val amount: Double,
    val description: String,
    val paymentDate: Date,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    var payer: User,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    var group: Group
)
