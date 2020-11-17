package com.github.yokitoki.zakupy.mpp.domain.repository

import com.github.yokitoki.zakupy.mpp.domain.entity.MainListItem
import com.github.yokitoki.zakupy.mpp.domain.entity.toDomain
import dev.icerock.moko.network.generated.apis.ListApi

class ListRepository internal constructor(private val listApi: ListApi) {
    suspend fun all(): List<MainListItem> {
        return listApi.v1ListGet().data.map {
            it.toDomain()
        }
    }
}
