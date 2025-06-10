package com.example.entity.user

import com.example.entity.common.BaseCreateBy
import com.example.entity.common.BaseModifiedBy
import com.example.entity.common.BaseTime
import org.babyfish.jimmer.sql.*

@Entity
@Table(
    name = "position"
)
interface Position : BaseCreateBy, BaseModifiedBy, BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    val name: String

    @ManyToMany(mappedBy = "positions")
    val users: List<User>
}