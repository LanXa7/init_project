package com.example.interceptor


import com.example.entity.common.BaseModifiedBy
import com.example.entity.common.BaseModifiedByDraft
import com.example.utils.StpUtils
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.springframework.stereotype.Component

@Component
class BaseModifiedByDraftInterceptor : DraftInterceptor<BaseModifiedBy, BaseModifiedByDraft> {

    override fun beforeSave(draft: BaseModifiedByDraft, original: BaseModifiedBy?) {
        if (!isLoaded(draft, BaseModifiedBy::modifiedBy)) {
                draft.modifiedById = StpUtils.getCurrentUserId()
        }
    }

}