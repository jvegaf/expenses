package me.jvegaf.group.usecase

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import me.jvegaf.expense.domain.Expense
import me.jvegaf.group.domain.Group
import me.jvegaf.group.domain.GroupRepository
import me.jvegaf.user.domain.User
import me.jvegaf.user.domain.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

@MicronautTest
class GroupServiceTest {

    @Inject
    lateinit var groupService: GroupService
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var groupRepository: GroupRepository
    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
        groupRepository.deleteAll()
    }


    @Test
    fun shouldAddMemberToGroup() {
        givenGroupCreated()
        val jose = User(null, "Francisco Buyo")
        userRepository.save(jose)
        val groupid: Int? = groupRepository.findByName("group1").orElseThrow().id
        val foundGroup = groupService.addMember(groupid!!, jose)
        assertEquals(2, foundGroup.members.size)
    }

    private fun givenGroupCreated() {
        val guti = User(null, "José María Gutiérrez")
        userRepository.save(guti)
        val group = Group(null, "group1", mutableListOf(guti))
        groupRepository.save(group)
    }

    @Test
    fun shouldRemoveMemberFromGroup() {
        givenGroupCreated()
        val jose    = User(null, "pepe")
        userRepository.save(jose)
        val groupId: Int? = groupRepository.findByName("group1").get().id
        val foundGroup = groupService.removeMember(groupId!!, jose)
        assertEquals(1, foundGroup.members.size)
    }

    private fun givenAGroupWithMembersAndExpenses() {
        givenGroupCreated()
        val alfon = User(null, "Alfonso Pérez")
        userRepository.save(alfon)
        val paco = User(null, "Francisco Buyo")
        userRepository.save(paco)
        val raul = User(null, "Raúl González")
        userRepository.save(raul)



        val group = groupRepository.findByName("group1").orElseThrow()
        groupService.addMember(group.id!!,alfon)
        groupService.addMember(group.id!!, raul)
        groupService.addMember(group.id!!, paco)

        group?.expenses?.add(Expense(null, 100.0, "cena", LocalDateTime.now(), paco, group))
        group?.expenses?.add(Expense(null, 10.0, "taxi", LocalDateTime.now(), alfon, group))
        group?.expenses?.add(Expense(null,   53.4, "compra", LocalDateTime.now(), alfon, group))

        groupRepository.save(group)
    }

    @Test
    fun shouldGetBalance() {
        givenAGroupWithMembersAndExpenses()
        val group = groupRepository.findByName("group1").orElseThrow()
        val balance = groupService.getBalance(group.id!!)
        assertEquals(4, balance.size)
        assertEquals(-40.85, balance.first().balance)
        assertEquals(59.15      , balance.last().balance)
    }
}