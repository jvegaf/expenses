package me.jvegaf.domain.expense

import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.*
import me.jvegaf.domain.group.Group
import me.jvegaf.domain.user.User
import java.util.Date

@Serdeable
@Entity
@Table(name = "expenses")
class Expense(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    @Column(name = "amount", nullable = false)
    var amount: Double,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "payment_date", nullable = false)
    var paymentDate: Date,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var payer: User,

    @ManyToOne
    @Column(name = "group_id")
    var group: Group
)
