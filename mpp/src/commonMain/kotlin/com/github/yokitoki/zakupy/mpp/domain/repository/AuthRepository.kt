package com.github.yokitoki.zakupy.mpp.domain.repository

import dev.icerock.moko.network.generated.apis.AuthApi
import dev.icerock.moko.network.generated.models.SignInRequest
import com.github.yokitoki.zakupy.mpp.domain.entity.AuthCredentials
import com.github.yokitoki.zakupy.mpp.domain.entity.toDomain
import com.github.yokitoki.zakupy.mpp.domain.storage.KeyValueStorage

class AuthRepository internal constructor(
    private val authApi: AuthApi,
    private val keyValueStorage: KeyValueStorage
) {
    suspend fun signIn(email: String, password: String): AuthCredentials {
        val request = SignInRequest(email, password)
        return authApi.signIn(request).data.toDomain()
    }
}
