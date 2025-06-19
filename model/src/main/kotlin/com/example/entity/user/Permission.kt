package com.example.entity.user

import com.example.entity.common.BaseCreateBy
import com.example.entity.common.BaseModifiedBy
import com.example.entity.common.BaseTime
import com.example.enums.EPermission
import org.babyfish.jimmer.sql.*

@Entity
@Table(
    name = "mkga.permission"
)
interface Permission : BaseCreateBy, BaseModifiedBy, BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    @Key
    val name: EPermission

    @Key
    val code: EPermission

    @ManyToMany(mappedBy = "permissions")
    val roles: List<Role>
}