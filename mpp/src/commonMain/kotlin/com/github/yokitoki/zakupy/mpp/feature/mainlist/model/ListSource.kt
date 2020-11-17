package com.github.yokitoki.zakupy.mpp.feature.mainlist.model

interface ListSource<T> {
    suspend fun getList(): List<T>
}
