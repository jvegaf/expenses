package me.jvegaf.expense.domain

import me.jvegaf.group.domain.Group
import me.jvegaf.user.domain.User
import me.jvegaf.shared.SortingAndOrderArguments
import org.reactivestreams.Publisher

interface ExpenseRepository {
    fun findById(id: Long): Publisher<Expense?>
    fun save(expense: Expense): Publisher<Expense>
    fun deleteById(id: Long)
    fun update(expense: Expense): Publisher<Int?>
    fun findAll(args: SortingAndOrderArguments): Publisher<Expense?>
    fun findAllByUser(user: User): Publisher<Expense?>
    fun findAllByGroup(group: Group): Publisher<Expense?>
}
