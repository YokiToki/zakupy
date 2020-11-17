package com.github.yokitoki.zakupy.mpp.domain.entity

import dev.icerock.moko.network.generated.models.AuthCredentialsResponseAllOfData
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class AuthCredentials(
    val accessToken: String,
    val refreshToken: String,
) : Parcelable

internal fun AuthCredentialsResponseAllOfData.toDomain(): AuthCredentials = AuthCredentials(
    accessToken = accessToken,
    refreshToken = refreshToken
)
