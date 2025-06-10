package com.example.interceptor

import com.example.entity.common.BaseTime
import com.example.entity.common.BaseTimeDraft
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class BaseTimeDraftInterceptor : DraftInterceptor<BaseTime, BaseTimeDraft> {
    override fun beforeSave(draft: BaseTimeDraft, original: BaseTime?) {
        if (!isLoaded(draft, BaseTime::modifiedAt)) {
            draft.createdAt = Instant.now()
        }
        if (original === null && !isLoaded(draft, BaseTime::createdAt)) {
            draft.createdAt = Instant.now()
        }
    }
}