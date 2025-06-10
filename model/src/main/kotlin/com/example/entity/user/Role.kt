package com.example.entity.user

import com.example.entity.common.BaseCreateBy
import com.example.entity.common.BaseModifiedBy
import com.example.entity.common.BaseTime
import com.example.enums.ERole
import org.babyfish.jimmer.sql.*

@Entity
@Table(
    name = "role"
)
interface Role : BaseCreateBy, BaseModifiedBy, BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    @Key
    val name: ERole

    @Key
    val code: ERole

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