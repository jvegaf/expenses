package me.jvegaf.group.infrastructure

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import me.jvegaf.group.domain.AddGroupRequest
import me.jvegaf.group.domain.Group
import me.jvegaf.group.usecase.GroupService
import me.jvegaf.user.usecase.UserService

@Controller("/api/groups")
class GroupController(private val groupService: GroupService, private val userService: UserService) {
    @Post
    fun create(
        @Body groupRequest: AddGroupRequest,
    ): Group {
        val user = userService.findById(groupRequest.creator)
        val group = Group(null, groupRequest.name, listOf(user))
        groupService.save(group)
        return group
    }

    @Get("/{id}")
    fun findById(id: Long): Group? {
        return groupService.findById(id)
    }
}
