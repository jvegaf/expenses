package me.jvegaf.group.domain

import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.*
import me.jvegaf.expense.domain.Expense
import me.jvegaf.user.domain.User

@Serdeable
@Entity
@Table(name = "groups")
class Group (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    @Column(name = "name", nullable = false)
    var name: String,

    @OneToMany(mappedBy = "group")
    var expenses: List<Expense>,

    @OneToMany(mappedBy = "group")
    var friends: List<User>

)