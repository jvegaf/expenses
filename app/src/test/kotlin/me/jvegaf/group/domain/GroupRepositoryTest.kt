package me.jvegaf.group.domain

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import me.jvegaf.user.domain.User
import me.jvegaf.user.domain.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@MicronautTest
class GroupRepositoryTest {

     @Inject
     lateinit var userRepository: UserRepository
     @Inject
     lateinit var groupRepository: GroupRepository
     @BeforeEach
     fun setup() {
         userRepository.deleteAll()
         groupRepository.deleteAll()
     }

    @Test
    fun shouldCreateANewGroup() {
        val user = User(null, "paul")
        userRepository.save(user)
        val group = Group(null, "group1", mutableListOf(user))
        groupRepository.save(group)

        val foundGroup = groupRepository.findById(group.id!!)
        Assertions.assertEquals(group, foundGroup.get())
    }

}