package com.github.yokitoki.zakupy.mpp

import com.github.aakira.napier.Antilog
import com.github.aakira.napier.Napier
import com.github.yokitoki.zakupy.mpp.domain.di.DomainFactory
import com.github.yokitoki.zakupy.mpp.domain.entity.MainListItem
import com.github.yokitoki.zakupy.mpp.domain.entity.MainListVisibility
import com.github.yokitoki.zakupy.mpp.feature.mainlist.di.ListFactory
import com.github.yokitoki.zakupy.mpp.feature.mainlist.model.ListSource
import com.github.yokitoki.zakupy.mpp.feature.mainlist.presentation.ListViewModel
import com.russhwolf.settings.Settings
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.units.TableUnitItem
import kotlin.random.Random

@Suppress("MagicNumber")
class SharedFactory(
    settings: Settings,
    antiLog: Antilog,
    baseUrl: String,
    mainListUnitsFactory: MainListUnitsFactory
) {
    private val domainFactory = DomainFactory(
        settings = settings,
        baseUrl = baseUrl
    )

    val listFactory: ListFactory<MainListItem> = ListFactory(
        listSource = object : ListSource<MainListItem> {
            override suspend fun getList(): List<MainListItem> {
                return domainFactory.listRepository.all()
            }
        },
        strings = object : ListViewModel.Strings {
            override val unknownError: StringResource = MR.strings.unknown_error
        },
        unitsFactory = object : ListViewModel.UnitsFactory<MainListItem> {
            override fun createTableUnitItem(data: MainListItem): TableUnitItem {
                return mainListUnitsFactory.createMainListItem(
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

    interface MainListUnitsFactory {
        fun createMainListItem(
            id: Long,
            name: String,
            visibility: MainListVisibility
        ): TableUnitItem
    }
}