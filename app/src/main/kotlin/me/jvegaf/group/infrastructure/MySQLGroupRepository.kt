package me.jvegaf.group.infrastructure

import me.jvegaf.ApplicationConfiguration
import me.jvegaf.group.domain.Group
import me.jvegaf.group.domain.GroupRepository
import me.jvegaf.shared.SortingAndOrderArguments
import org.hibernate.SessionFactory
import org.hibernate.reactive.stage.Stage
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux

class MySQLGroupRepository (
    val applicationConfiguration: ApplicationConfiguration,
    sessionFactory: SessionFactory,
): GroupRepository {
    private val sessionFactory: Stage.SessionFactory
    private val VALID_PROPERTY_NAMES = arrayOf("id", "name")

    init {
        this.sessionFactory = sessionFactory.unwrap(Stage.SessionFactory::class.java)
    }

    override fun save(group: Group): Publisher<Group> {
        return Mono.fromCompletionStage(sessionFactory.withTransaction { session ->
            session.persist(group)
                .thenApply { group }
        })
    }

    override fun update(group: Group): Publisher<Int?> {
        return Mono.fromCompletionStage(sessionFactory.withTransaction { session ->
            session.createQuery<Long>("UPDATE group g SET name = :name where id = :id")
                .setParameter("name", group.name)
                .setParameter("id", group.id)
                .executeUpdate()
        })
    }

    override fun findById(id: Long): Publisher<Group?> {
        return Mono.fromCompletionStage(sessionFactory.withTransaction { session ->
            session.find(Group::class.java, id)
        })
    }

    override fun findAll(args: SortingAndOrderArguments): Publisher<Group?> {
        val qlString: String = createQuery(args)
        return Mono.fromCompletionStage(sessionFactory.withSession { session ->
            val query = session.createQuery(
                qlString,
                Group::class.java
            )
            query.maxResults = args.max ?: applicationConfiguration.max
            args.offset?.let { query.setFirstResult(it) }
            query.resultList
        })
            .flatMapMany { Flux.fromIterable(it) }
    }

    override fun deleteById(id: Long) {
        sessionFactory.withTransaction { session ->
            session.find(Group::class.java, id).thenApply { entity ->
                session.remove(entity)
            }
        }
    }

    private fun createQuery(args: SortingAndOrderArguments): String {
        var qlString = "SELECT g FROM group as g"
        val order = args.order
        val sort = args.sort
        if (order != null && sort != null && VALID_PROPERTY_NAMES.contains(sort)) {
            qlString += " ORDER BY g." + sort + ' ' + order.lowercase()
        }
        return qlString
    }
}
