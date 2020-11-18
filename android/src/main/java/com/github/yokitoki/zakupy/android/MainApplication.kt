package com.github.yokitoki.zakupy.android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.github.aakira.napier.DebugAntilog
import com.github.yokitoki.zakupy.android.units.RosterUnitsFactory
import com.github.yokitoki.zakupy.mpp.SharedFactory
import com.russhwolf.settings.AndroidSettings

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppComponent.factory = SharedFactory(
            baseUrl = BuildConfig.BASE_URL,
            settings = AndroidSettings(getSharedPreferences("app", MODE_PRIVATE)),
            antiLog = DebugAntilog(),
            rosterUnitsFactory = RosterUnitsFactory()
        )
        
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}