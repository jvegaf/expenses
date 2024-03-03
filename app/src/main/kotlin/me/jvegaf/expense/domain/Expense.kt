package me.jvegaf.expense.domain

import jakarta.persistence.*
import me.jvegaf.group.domain.Group
import me.jvegaf.user.domain.User
import java.util.*

@Entity
@Table(name = "expenses")
class Expense(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var amount: Double,
    var description: String,
    var paymentDate: Date,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "payer_id", referencedColumnName = "id")
    var payer: User,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    var group: Group
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Expense

        if (id != other.id) return false
        if (amount != other.amount) return false
        if (description != other.description) return false
        if (paymentDate != other.paymentDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + amount.hashCode()
        return result
    }
}
