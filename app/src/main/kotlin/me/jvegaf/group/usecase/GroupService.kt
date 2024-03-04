package me.jvegaf.group.usecase

import jakarta.inject.Singleton
import me.jvegaf.group.domain.*
import me.jvegaf.user.domain.User
import java.util.*

@Singleton
class GroupService(private val repository: GroupRepository) {
    fun save(group: Group): Group {
        return repository.save(group)
    }

    fun findById(id: Int): Optional<Group> {
        return repository.findById(id)
    }

    fun addMember(groupId: Int, user: User): Group {
        val group = repository.findById(groupId).orElseThrow()
        group.members?.add(user)
        return repository.save(group)

    }

    fun removeMember(groupId: Int, user: User): Group {
        val group = repository.findById(groupId).orElseThrow()
        group.members?.remove(user)
        return repository.save(group)
    }

    fun getBalance(groupId: Int): GroupBalance {
        val group = repository.findById(groupId).orElseThrow()
        return group.members?.map { user ->
            val userExpensesTotal: Double? = group.expenses
                ?.filter { user.id == it.payer.id }?.sumOf { it.amount }
                ?.minus(group.totalExpenses() / group.members!!.size)
            UserBalance(user.name, userExpensesTotal ?: 0)
        } ?: emptyList()
    }
}
