package me.jvegaf.expense.domain

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ExpenseRepository: JpaRepository<Expense, Int>  {

    fun findByGroupId(groupId: Int): List<Expense>

}
