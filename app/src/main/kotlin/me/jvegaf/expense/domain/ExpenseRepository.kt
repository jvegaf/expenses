package me.jvegaf.expense.domain

import io.micronaut.data.annotation.Join
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaSpecificationExecutor
import io.micronaut.data.repository.CrudRepository

@Repository
interface ExpenseRepository: CrudRepository<Expense, Long> , JpaSpecificationExecutor<Expense> {

//    @Join(value = "payer", type = Join.Type.FETCH)
//    fun listByPayer(): List<Expense>

//    @Join(value = "group", type = Join.Type.FETCH)
//    fun listByGroup(): List<Expense>
}
