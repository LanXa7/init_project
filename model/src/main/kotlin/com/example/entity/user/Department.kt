package com.example.entity.user

import com.example.entity.common.BaseCreateBy
import com.example.entity.common.BaseModifiedBy
import com.example.entity.common.BaseTime
import org.babyfish.jimmer.sql.*

@Entity
@Table(
    name = "mkga.department",
)
interface Department: BaseCreateBy, BaseModifiedBy, BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    val name: String

    @ManyToOne
    @JoinColumn(name = "parent_id")
    val parent: Department?

    @OneToMany(mappedBy = "parent")
    val children: List<Department>

    @ManyToMany(mappedBy = "departments")
    val users: List<User>


}