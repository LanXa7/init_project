package com.example.entity.user

import com.example.entity.common.BaseModifiedBy
import com.example.entity.common.BaseTime
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "mkga.user")
interface User : BaseModifiedBy, BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    @Key
    val username: String

    val password: String

    @Key
    val email: String

    val avatar: String?

    val enable: Boolean

    @ManyToMany
    @JoinTable(
        name = "user_role_mapping",
        joinColumnName = "user_id",
        inverseJoinColumnName = "role_id",
    )
    val roles: List<Role>

    @ManyToMany
    @JoinTable(
        name = "user_department_mapping",
        joinColumnName = "user_id",
        inverseJoinColumnName = "department_id",
    )
    val departments: List<Department>

    @ManyToMany
    @JoinTable(
        name = "user_position_mapping",
        joinColumnName = "user_id",
        inverseJoinColumnName = "position_id",
    )
    val positions: List<Position>
}