package com.github.yokitoki.zakupy.mpp.feature.roster.di

import com.github.yokitoki.zakupy.mpp.feature.roster.model.RosterSource
import com.github.yokitoki.zakupy.mpp.feature.roster.presentation.RosterViewModel

class RosterFactory<T>(
    private val rosterSource: RosterSource<T>,
    private val strings: RosterViewModel.Strings,
    private val unitsFactory: RosterViewModel.UnitsFactory<T>
) {
    fun createListViewModel(): RosterViewModel<T> {
        return RosterViewModel(
            rosterSource = rosterSource,
            strings = strings,
            unitsFactory = unitsFactory
        )
    }
}
