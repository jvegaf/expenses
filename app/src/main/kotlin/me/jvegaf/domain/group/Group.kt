package me.jvegaf.domain.group

import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.*
import me.jvegaf.domain.expense.Expense
import me.jvegaf.domain.user.User

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