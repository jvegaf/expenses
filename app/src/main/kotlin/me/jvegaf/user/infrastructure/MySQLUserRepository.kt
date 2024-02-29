package me.jvegaf.user.infrastructure

import io.vertx.sqlclient.impl.command.ExtendedQueryCommand.createQuery
import jakarta.inject.Singleton
import me.jvegaf.ApplicationConfiguration
import me.jvegaf.shared.SortingAndOrderArguments
import me.jvegaf.user.domain.User
import me.jvegaf.user.domain.UserRepository
import org.hibernate.SessionFactory
import org.hibernate.reactive.stage.Stage
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Singleton
open class MySQLUserRepository(
    val applicationConfiguration: ApplicationConfiguration,
    sessionFactory: SessionFactory
) : UserRepository {
    private val sessionFactory: Stage.SessionFactory
    private val VALID_PROPERTY_NAMES = arrayOf("id", "email")

    init {
        this.sessionFactory = sessionFactory.unwrap(Stage.SessionFactory::class.java)
    }

    override fun findById(id: Long): Publisher<User?> {
        return Mono.fromCompletionStage(sessionFactory.withTransaction { session ->
            session.find(User::class.java, id)
        })
    }

    override fun findByEmail(email: String): Publisher<User?> {
        return Mono.fromCompletionStage(sessionFactory.withTransaction { session ->
            session.find(User::class.java, email)
        })
    }

    override fun findAll(args: SortingAndOrderArguments): Publisher<User?> {
        val qlString: String = createQuery(args)
        return Mono.fromCompletionStage(sessionFactory.withSession { session ->
            val query = session.createQuery(qlString, User::class.java)
            query.maxResults = args.max ?: applicationConfiguration.max
            args.offset?.let { query.firstResult(it) }
            query.resultList
        })
            .flatMapMany { Flux.fromIterable(it) }
    }

    private fun createQuery(args: SortingAndOrderArguments): String {
        var qlString = "SELECT u FROM user as u"
        val order = args.order
        val sort = args.sort
        if (order != null && sort != null && VALID_PROPERTY_NAMES.contains(sort)) {
            qlString += " ORDER BY u." + sort + ' ' + order.lowercase()
        }
        return qlString
    }

    override fun save(user: User): Publisher<User> {
        return Mono.fromCompletionStage(sessionFactory.withTransaction { session ->
            session.persist(user)
                .thenApply { user }
        })
    }

    override fun deleteById(id: Long) {
        sessionFactory.withTransaction { session ->
            session.find(User::class.java, id).thenApply { entity ->
                session.remove(entity)
            }
        }
    }
}
