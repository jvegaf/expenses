package me.jvegaf.user.domain

import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.*
import me.jvegaf.expense.domain.Expense
import me.jvegaf.group.domain.Group


@Serdeable
@Entity
@Table(name = "users")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @OneToMany(mappedBy = "user")
    var expenses: List<Expense>,

    @ManyToMany
    @JoinTable(
        name = "users_groups",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    var groups: List<Group>
)
