package me.jvegaf.group.infrastructure

import io.micronaut.http.annotation.*
import me.jvegaf.expense.domain.AddExpense
import me.jvegaf.expense.domain.Expense
import me.jvegaf.expense.usecase.ExpenseService
import me.jvegaf.group.domain.*
import me.jvegaf.group.usecase.GroupService
import me.jvegaf.user.domain.User
import me.jvegaf.user.usecase.UserService
import java.util.Optional

@Controller("/api/groups")
class GroupController(
    private val groupService: GroupService,
    private val expenseService: ExpenseService,
    private val userService: UserService) {
    @Post
    fun create(
        @Body groupRequest: AddGroup,
    ): Group {
        val user: User = userService.findById(groupRequest.creator).orElseThrow()
        val group = Group(null, groupRequest.name, mutableListOf(user))
        groupService.save(group)
        return group
    }


    @Get("/{id}")
    fun findById(id: Int): Optional<Group> {
        return groupService.findById(id)
    }
    @Get("/{id}/expenses")
    fun findExpensesByGroupId(id: Int): List<Expense>? {
        return expenseService.findExpensesFromGroupId(id)
    }

    @Post("/{groupId}/expenses")
    fun createExpense(
        @Body request: AddExpense,
        @PathVariable groupId: Int
    ): Expense {
        val creator: User = userService.findById(request.creator).orElseThrow()
        val group: Group = groupService.findById(groupId).orElseThrow()
        val expense =
            Expense(
                null,
                request.amount,
                request.description,
                request.paymentDate,
                creator,
                group
            )
        return expenseService.save(expense)
    }

    @Post("/{groupId}/members")
    fun addMember(
        @Body request: AddGroupUser,
        @PathVariable groupId: Int
    ): Group {
        val user = userService.findById(request.userId).orElseThrow()
        return groupService.addMember(groupId, user)
    }

    @Get("/{groupId}/members")
    fun findMembers(@PathVariable groupId: Int): List<User> {
        val group: Group = groupService.findById(groupId).orElseThrow()
        return group.members?.toList() ?: listOf()
    }

    fun getBalance(@PathVariable groupId: Int): GroupBalance {
        return groupService.getBalance(groupId)
    }

}
