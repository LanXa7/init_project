package com.example.entity.common

import org.babyfish.jimmer.sql.MappedSuperclass
import java.time.Instant

@MappedSuperclass
interface BaseTime {
    val createdAt: Instant

    val modifiedAt: Instant
}