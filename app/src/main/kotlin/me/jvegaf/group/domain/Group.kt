package me.jvegaf.group.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.*
import me.jvegaf.expense.domain.Expense
import me.jvegaf.user.domain.User

@Entity
@Table(name = "groups")
@Serdeable
class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,
    var name: String,

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = [ CascadeType.PERSIST, CascadeType.MERGE ])
    @JsonIgnoreProperties("groups")
    var members: MutableList<User>? = mutableListOf(),

    @OneToMany(fetch = FetchType.EAGER, cascade = [ CascadeType.ALL ], orphanRemoval = true)
    var expenses: MutableList<Expense>? = mutableListOf(),
) {
    fun totalExpenses(): Double {
        return (expenses?.sumOf { it.amount } ?: 0) as Double
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Group

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
