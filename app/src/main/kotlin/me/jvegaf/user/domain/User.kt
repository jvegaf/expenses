package me.jvegaf.user.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.*
import me.jvegaf.group.domain.Group


@Entity
@Table(name = "users")
@Serdeable
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,
    var name: String,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinTable(name = "user_group",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "group_id", referencedColumnName = "id")])

    @JsonIgnoreProperties("friends")
    var groups: List<Group> = mutableListOf(),

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        return result
    }
}
