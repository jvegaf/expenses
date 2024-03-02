package me.jvegaf.group.usecase

import jakarta.inject.Singleton
import me.jvegaf.group.domain.Group
import me.jvegaf.group.domain.GroupRepository

@Singleton
class GroupService(val repository: GroupRepository) {
    fun save(group: Group): Group {
        return repository.save(group)
    }

    fun findById(id: Long): Group? {
        return repository.findById(id).orElse(null)
    }
}
