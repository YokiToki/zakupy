package com.github.yokitoki.zakupy.mpp.feature.roster.model

interface RosterSource<T> {
    suspend fun getList(): List<T>
}
