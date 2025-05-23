package com.example.entity.user

import org.babyfish.jimmer.sql.*

@Entity
@Table(
    name = "role"
)
interface Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    @Key
    val code: Role

    @ManyToMany(mappedBy = "roles")
    val users: List<User>

    @ManyToMany
    @JoinTable(
        name = "role_permission_mapping",
        joinColumnName = "role_id",
        inverseJoinColumnName = "permission_id",
    )
    val permissions: List<Permission>

}