package com.github.yokitoki.zakupy.mpp.domain.entity

import dev.icerock.moko.network.generated.models.RosterListItemResponse
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

enum class RosterVisibility {
    PERSONAL,
    SHARED
}

enum class RosterType {
    BASIC
}

@Parcelize
data class Roster(
    val uuid: String,
    val name: String,
    val visibility: RosterVisibility,
    val type: RosterType,
) : Parcelable

internal fun RosterListItemResponse.toDomain(): Roster = Roster(
    uuid = uuid,
    name = name,
    visibility = RosterVisibility.valueOf(visibility.toString()),
    type = RosterType.valueOf(type.toString())
)
