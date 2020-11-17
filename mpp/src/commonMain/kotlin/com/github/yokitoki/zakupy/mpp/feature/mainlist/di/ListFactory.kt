package com.github.yokitoki.zakupy.mpp.feature.mainlist.di

import com.github.yokitoki.zakupy.mpp.feature.mainlist.model.ListSource
import com.github.yokitoki.zakupy.mpp.feature.mainlist.presentation.ListViewModel

class ListFactory<T>(
    private val listSource: ListSource<T>,
    private val strings: ListViewModel.Strings,
    private val unitsFactory: ListViewModel.UnitsFactory<T>
) {
    fun createListViewModel(): ListViewModel<T> {
        return ListViewModel(
            listSource = listSource,
            strings = strings,
            unitsFactory = unitsFactory
        )
    }
}
