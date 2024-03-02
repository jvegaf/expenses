package me.jvegaf.group.domain

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface GroupRepository: CrudRepository<Group, Long> {

}
