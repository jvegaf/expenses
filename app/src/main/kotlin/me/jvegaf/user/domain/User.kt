package me.jvegaf.user.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import me.jvegaf.expense.domain.Expense
import me.jvegaf.group.domain.Group


@Entity
data class User (
    @Id
    @GeneratedValue
    var id: Long?,
    val email: String,
    @JsonIgnore
    val password: String,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(name = "user_group",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "group_id", referencedColumnName = "id")])

    @JsonIgnoreProperties("friends")
    var groups: List<Group> = mutableListOf()
)
