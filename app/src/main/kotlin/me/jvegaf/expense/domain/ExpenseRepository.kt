package me.jvegaf.expense.domain

import io.micronaut.data.annotation.Join
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.data.jpa.repository.JpaSpecificationExecutor
import io.micronaut.data.repository.CrudRepository

@Repository
interface ExpenseRepository: JpaRepository<Expense, Int>  {

    fun findByGroupId(groupId: Int): List<Expense>

}
