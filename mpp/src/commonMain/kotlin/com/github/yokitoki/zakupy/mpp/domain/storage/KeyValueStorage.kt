package com.github.yokitoki.zakupy.mpp.domain.storage

import com.russhwolf.settings.Settings
import com.russhwolf.settings.nullableString

class KeyValueStorage(settings: Settings) {
    var token by settings.nullableString("pref_token")
    var language by settings.nullableString("pref_language")
}
