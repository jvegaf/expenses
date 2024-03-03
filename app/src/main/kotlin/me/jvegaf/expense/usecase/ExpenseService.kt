package me.jvegaf.expense.usecase

import jakarta.inject.Singleton
import me.jvegaf.expense.domain.Expense
import me.jvegaf.expense.domain.ExpenseRepository


@Singleton
class ExpenseService(private val repository: ExpenseRepository) {
    fun findExpensesFromGroupId(groupId: Int): List<Expense> {
        return repository.findByGroupId(groupId)
    }

    fun save(expense: Expense): Expense {
        return repository.save(expense)
    }

}
