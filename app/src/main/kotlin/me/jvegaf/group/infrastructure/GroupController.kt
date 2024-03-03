package me.jvegaf.group.infrastructure

import io.micronaut.http.annotation.*
import me.jvegaf.expense.domain.AddExpense
import me.jvegaf.expense.domain.Expense
import me.jvegaf.expense.usecase.ExpenseService
import me.jvegaf.group.domain.AddGroup
import me.jvegaf.group.domain.AddGroupUser
import me.jvegaf.group.domain.Group
import me.jvegaf.group.domain.GroupBalance
import me.jvegaf.group.usecase.GroupService
import me.jvegaf.user.domain.User
import me.jvegaf.user.usecase.UserService

@Controller("/api/groups")
class GroupController(
    private val groupService: GroupService,
    private val expenseService: ExpenseService,
    private val userService: UserService) {
    @Post
    fun create(
        @Body groupRequest: AddGroup,
    ): Group {
        val user: User = userService.findById(groupRequest.creator) as User
        val group = Group(null, groupRequest.name, listOf(user))
        groupService.save(group)
        return group
    }


    @Get("/{id}")
    fun findById(id: Int): Group? {
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
        val user: User = (userService.findById(request.creator) ?: throw Exception()) as User
        val group: Group = groupService.findById(groupId) ?: throw Exception()
        val expense =
            Expense(
                null,
                request.amount,
                request.description,
                request.paymentDate,
                user,
                group
            )
        return expenseService.save(expense)
    }

    @Post("/{groupId}/members")
    fun addMember(
        @Body request: AddGroupUser,
        @PathVariable groupId: Int
    ): Group {
        val user = userService.findById(request.userId) ?: throw Exception()
        return groupService.addMember(groupId, user)
    }

    @Get("/{groupId}/members")
    fun findMembers(@PathVariable groupId: Int): List<User> {
        val group = groupService.findById(groupId) ?: throw Exception()
        return group.members
    }

    @Delete("/{groupId}/members/{userId}")
    fun deleteMember(
        @PathVariable groupId: Int,
        @PathVariable userId: Int
    ): Group {
        val user: User = (userService.findById(userId) ?: throw Exception()) as User
        return groupService.removeMember(groupId, user)
    }

    @Get("/{groupId}/balance")
    fun getBalance(@PathVariable groupId: Int): GroupBalance {
        return groupService.getBalance(groupId)
    }
}
