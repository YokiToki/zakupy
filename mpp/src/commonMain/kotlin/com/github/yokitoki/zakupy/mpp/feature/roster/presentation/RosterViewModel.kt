package com.github.yokitoki.zakupy.mpp.feature.roster.presentation

import com.github.aakira.napier.Napier
import dev.icerock.moko.mvvm.State
import dev.icerock.moko.mvvm.asState
import dev.icerock.moko.mvvm.livedata.*
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.units.TableUnitItem
import com.github.yokitoki.zakupy.mpp.feature.roster.model.RosterSource
import kotlinx.coroutines.launch

class RosterViewModel<T>(
    private val rosterSource: RosterSource<T>,
    private val strings: Strings,
    private val unitsFactory: UnitsFactory<T>
) : ViewModel() {

    private val _state: MutableLiveData<State<List<T>, Throwable>> =
        MutableLiveData(initialValue = State.Loading())

    val state: LiveData<State<List<TableUnitItem>, StringDesc>> = _state
        .dataTransform {
            map { items ->
                items.map { unitsFactory.createTableUnitItem(it) }
            }
        }
        .errorTransform {
            map { it.message?.desc() ?: strings.unknownError.desc() }
        }

    init {
        loadList()
    }

    fun onRetryPressed() {
        loadList()
    }

    fun onRefresh(completion: () -> Unit) {
        viewModelScope.launch {
            @Suppress("TooGenericExceptionCaught")
            try {
                val items = rosterSource.getList()

                _state.value = items.asState()
            } catch (error: Throwable) {
                Napier.e("can't refresh", throwable = error)
            } finally {
                completion()
            }
        }
    }

    private fun loadList() {
        viewModelScope.launch {
            @Suppress("TooGenericExceptionCaught")
            try {
                _state.value = State.Loading()

                val items = rosterSource.getList()

                _state.value = items.asState()
            } catch (error: Throwable) {
                _state.value = State.Error(error)
            }
        }
    }

    interface UnitsFactory<T> {
        fun createTableUnitItem(data: T): TableUnitItem
    }

    interface Strings {
        val unknownError: StringResource
    }
}
