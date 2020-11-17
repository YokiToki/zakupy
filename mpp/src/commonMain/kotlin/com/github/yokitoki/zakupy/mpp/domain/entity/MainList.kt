package com.github.yokitoki.zakupy.mpp.domain.entity

import dev.icerock.moko.network.generated.models.MainListResponseItem
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

enum class MainListVisibility {
    PERSONAL,
    SHARED
}

enum class MainListType {
    BASIC
}

@Parcelize
data class MainListItem(
    val uuid: String,
    val name: String,
    val visibility: MainListVisibility,
    val type: MainListType,
) : Parcelable

internal fun MainListResponseItem.toDomain(): MainListItem = MainListItem(
    uuid = uuid,
    name = name,
    visibility = MainListVisibility.valueOf(visibility.toString()),
    type = MainListType.valueOf(type.toString())
)
