package com.example.interceptor


import com.example.entity.common.BaseCreateBy
import com.example.entity.common.BaseCreateByDraft
import com.example.utils.StpUtils
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.springframework.stereotype.Component

@Component
class BaseCreatedByDraftInterceptor : DraftInterceptor<BaseCreateBy, BaseCreateByDraft> {

    override fun beforeSave(draft: BaseCreateByDraft, original: BaseCreateBy?) {
        if (original === null && !isLoaded(draft, BaseCreateBy::createdBy)) {
                draft.createdById = StpUtils.getCurrentUserId()
        }
    }

}