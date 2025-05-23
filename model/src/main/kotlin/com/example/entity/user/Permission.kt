package com.example.entity.user

import org.babyfish.jimmer.sql.*

@Entity
@Table(
    name = "permission"
)
interface Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    @Key
    val code: String

    @ManyToMany(mappedBy = "permissions")
    val roles: List<Role>
}