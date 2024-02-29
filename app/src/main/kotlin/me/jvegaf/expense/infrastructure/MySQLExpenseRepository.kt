package me.jvegaf.expense.infrastructure

import me.jvegaf.ApplicationConfiguration
import me.jvegaf.expense.domain.Expense
import me.jvegaf.expense.domain.ExpenseRepository
import me.jvegaf.shared.SortingAndOrderArguments
import me.jvegaf.group.domain.Group
import me.jvegaf.user.domain.User
import org.hibernate.SessionFactory
import org.hibernate.reactive.stage.Stage
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux

class MySQLExpenseRepository (
    val applicationConfiguration: ApplicationConfiguration,
    sessionFactory: SessionFactory,
): ExpenseRepository {
    private val sessionFactory: Stage.SessionFactory
    private val VALID_PROPERTY_NAMES = arrayOf("id", "name")

    init {
        this.sessionFactory = sessionFactory.unwrap(Stage.SessionFactory::class.java)
    }

    override fun save(expense: Expense): Publisher<Expense> {
        return Mono.fromCompletionStage(sessionFactory.withTransaction { session ->
            session.persist(expense)
                .thenApply { expense }
        })
    }

    override fun update(expense: Expense): Publisher<Int?> {
        return Mono.fromCompletionStage(sessionFactory.withTransaction { session ->
            session.createMutationQuery("UPDATE expense g SET amount = :amount description = :desc payment_date = :date user_id = :payer group_id = :group where id = :id")
                .setParameter("amount", expense.amount)
                .setParameter("desc", expense.description)
                .setParameter("payer", expense.payer)
                .setParameter("group", expense.group)
                .setParameter("id", expense.id)
                .executeUpdate()
        })
    }

    override fun findById(id: Long): Publisher<Expense?> {
        return Mono.fromCompletionStage(sessionFactory.withTransaction { session ->
            session.find(Expense::class.java, id)
        })
    }

    override fun findAll(args: SortingAndOrderArguments): Publisher<Expense?> {
        val qlString: String = createQuery(args)
        return Mono.fromCompletionStage(sessionFactory.withSession { session ->
            val query = session.createQuery(
                qlString,
                Expense::class.java
            )
            query.maxResults = args.max ?: applicationConfiguration.max
            args.offset?.let { query.setFirstResult(it) }
            query.resultList
        })
            .flatMapMany { Flux.fromIterable(it) }
    }

    override fun deleteById(id: Long) {
        sessionFactory.withTransaction { session ->
            session.find(Expense::class.java, id).thenApply { entity ->
                session.remove(entity)
            }
        }
    }

    private fun createQuery(args: SortingAndOrderArguments): String {
        var qlString = "SELECT e FROM expense as e"
        val order = args.order
        val sort = args.sort
        if (order != null && sort != null && VALID_PROPERTY_NAMES.contains(sort)) {
            qlString += " ORDER BY e." + sort + ' ' + order.lowercase()
        }
        return qlString
    }

    override fun findAllByUser(user: User): Publisher<Expense?> {
        return Mono.fromCompletionStage(sessionFactory.withSession { session ->
            var offset = 0
            val query = session.createQuery(
                "select e from expense as e where payer_id = :payer",
                Expense::class.java
            )
            .setParameter("payer", user.id)
            query.maxResults = applicationConfiguration.max
            offset.let { query.setFirstResult(it) }
            query.resultList
        })
            .flatMapMany { Flux.fromIterable(it) }
    }

    override fun findAllByGroup(group: Group): Publisher<Expense?> { 
        return Mono.fromCompletionStage(sessionFactory.withSession { session ->
            var offset = 0
            val query = session.createQuery(
                "select e from expense as e where group_id = :group",
                Expense::class.java
            )
            .setParameter("group", group.id)
            query.maxResults = applicationConfiguration.max
            offset.let { query.setFirstResult(it) }
            query.resultList
        })
            .flatMapMany { Flux.fromIterable(it) }
    }
}
