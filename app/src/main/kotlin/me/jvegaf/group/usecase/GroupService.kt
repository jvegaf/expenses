package me.jvegaf.group.usecase

import jakarta.inject.Singleton
import me.jvegaf.group.domain.Group
import me.jvegaf.group.domain.GroupBalance
import me.jvegaf.group.domain.GroupRepository
import me.jvegaf.group.domain.UserBalance
import me.jvegaf.user.domain.User

@Singleton
class GroupService(private val repository: GroupRepository) {
    fun save(group: Group): Group {
        return repository.save(group)
    }

    fun findById(id: Int): Group? {
        return repository.findById(id).orElse(null)
    }

    fun <User> addMember(groupId: Int, user: User): Group {
        val group = repository.findById(groupId).orElseThrow()
        group.members.plus(user)
        return repository.save(group)

    }

    fun removeMember(groupId: Int, user: User): Group {
        val group = repository.findById(groupId).orElseThrow()
        group.members.minus(user)
        return repository.save(group)
    }

    fun getBalance(groupId: Int): GroupBalance {
        val group = repository.findById(groupId).orElseThrow()
        return group.members.map {
            val userExpensesTotal: Double? = group.expenses
                ?.filter { it.id == it.payer.id }
                ?.map { it.amount }
                ?.sum()
                ?.minus(group.totalExpenses())
            UserBalance(it.name, userExpensesTotal ?: 0)
        }
    }
}
