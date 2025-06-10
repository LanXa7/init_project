package com.example.entity.common

import com.example.entity.user.User
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface BaseCreateBy {
    @ManyToOne
    @JoinColumn(name = "created_by", foreignKeyType = ForeignKeyType.FAKE)
    val createdBy: User?
}