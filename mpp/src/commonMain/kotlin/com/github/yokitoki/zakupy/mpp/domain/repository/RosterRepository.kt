package com.github.yokitoki.zakupy.mpp.domain.repository

import com.github.yokitoki.zakupy.mpp.domain.entity.Roster
import com.github.yokitoki.zakupy.mpp.domain.entity.toDomain
import dev.icerock.moko.network.generated.apis.RosterApi

class RosterRepository internal constructor(private val rosterApi: RosterApi) {
    suspend fun all(): List<Roster> {
        return rosterApi.v1RosterGet().data.map {
            it.toDomain()
        }
    }
}
