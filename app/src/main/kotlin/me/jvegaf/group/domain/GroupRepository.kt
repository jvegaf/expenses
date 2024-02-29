package me.jvegaf.group.domain

import org.reactivestreams.Publisher
import me.jvegaf.shared.SortingAndOrderArguments

interface GroupRepository {

    fun save(group: Group): Publisher<Group>

    fun update(group: Group): Publisher<Int?>

    fun findById(id: Long): Publisher<Group?>

    fun findAll(args: SortingAndOrderArguments): Publisher<Group?>

    fun deleteById(id: Long)

}
