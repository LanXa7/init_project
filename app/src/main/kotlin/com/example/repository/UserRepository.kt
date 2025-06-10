package com.example.repository

import com.example.entity.user.User
import com.example.entity.user.email
import com.example.entity.user.id
import com.example.entity.user.username
import org.babyfish.jimmer.spring.repo.support.AbstractKotlinRepository
import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.`eq?`
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    sql: KSqlClient
) : AbstractKotlinRepository<User, Long>(sql) {

    fun findUserRolePermissionBy(id: Long, fetcher: Fetcher<User>) =
        createQuery {
            where(table.id eq id)
            select(table.fetch(fetcher))
        }.fetchOneOrNull()

    fun isEmailOrUserNameExists(email: String, username: String? = null): Boolean =
        createQuery {
            where(
                or(
                    table.username `eq?` username,
                    table.email eq email
                )
            )
            select(table.id)
        }.exists()

    fun findOneOrNullByText(
        text: String,
        fetcher: Fetcher<User>? = null
    ) =
        createQuery {
            where(
                or(
                    table.username eq text,
                    table.email eq text
                )
            )
            select(table.fetch(fetcher))
        }.fetchOneOrNull()

}