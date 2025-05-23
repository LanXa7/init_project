package com.example.entity.user

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "\"user\"")
interface User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    @Key
    val username: String

    val password: String

    @Key
    val email: String

    val avatar: String?

    @ManyToMany
    @JoinTable(
        name = "user_role_mapping",
        joinColumnName = "user_id",
        inverseJoinColumnName = "role_id",
    )
    val roles: List<Role>

}