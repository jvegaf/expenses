package me.jvegaf.group.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import me.jvegaf.user.domain.User

@Entity
data class Group (
    @Id
    @GeneratedValue
    var id: Long?,
    val name: String,

    @ManyToMany(mappedBy = "groups")
    @JsonIgnoreProperties("groups")
    var friends: List<User> = mutableListOf(),

)