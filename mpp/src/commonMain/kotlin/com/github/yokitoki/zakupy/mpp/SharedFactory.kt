package com.github.yokitoki.zakupy.mpp

import com.github.aakira.napier.Antilog
import com.github.aakira.napier.Napier
import com.github.yokitoki.zakupy.mpp.domain.di.DomainFactory
import com.github.yokitoki.zakupy.mpp.domain.entity.Roster
import com.github.yokitoki.zakupy.mpp.domain.entity.RosterVisibility
import com.github.yokitoki.zakupy.mpp.feature.roster.di.RosterFactory
import com.github.yokitoki.zakupy.mpp.feature.roster.model.RosterSource
import com.github.yokitoki.zakupy.mpp.feature.roster.presentation.RosterViewModel
import com.russhwolf.settings.Settings
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.units.TableUnitItem
import kotlin.random.Random

@Suppress("MagicNumber")
class SharedFactory(
    settings: Settings,
    antiLog: Antilog,
    baseUrl: String,
    rosterUnitsFactory: RosterUnitsFactory
) {
    private val domainFactory = DomainFactory(
        settings = settings,
        baseUrl = baseUrl
    )

    val rosterFactory: RosterFactory<Roster> = RosterFactory(
        rosterSource = object : RosterSource<Roster> {
            override suspend fun getList(): List<Roster> {
                return domainFactory.rosterRepository.all()
            }
        },
        strings = object : RosterViewModel.Strings {
            override val unknownError: StringResource = MR.strings.unknown_error
        },
        unitsFactory = object : RosterViewModel.UnitsFactory<Roster> {
            override fun createTableUnitItem(data: Roster): TableUnitItem {
                return rosterUnitsFactory.createMainListItem(
                    id = Random(555L).nextLong(),
                    name = data.name,
                    visibility = data.visibility
                )
            }
        }
    )

    init {
        Napier.base(antiLog)
    }

    interface RosterUnitsFactory {
        fun createMainListItem(
            id: Long,
            name: String,
            visibility: RosterVisibility
        ): TableUnitItem
    }
}