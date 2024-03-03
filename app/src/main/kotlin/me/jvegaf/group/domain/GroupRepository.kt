package me.jvegaf.group.domain

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.data.repository.CrudRepository

@Repository
interface GroupRepository: JpaRepository<Group, Int> {

}
