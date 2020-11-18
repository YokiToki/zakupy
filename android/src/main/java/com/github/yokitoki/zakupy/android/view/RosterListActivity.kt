package com.github.yokitoki.zakupy.android.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.yokitoki.zakupy.android.AppComponent
import dev.icerock.moko.mvvm.MvvmActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import com.github.yokitoki.zakupy.android.R
import com.github.yokitoki.zakupy.android.BR
import com.github.yokitoki.zakupy.android.databinding.ActivityRosterListBinding
import com.github.yokitoki.zakupy.mpp.domain.entity.Roster
import com.github.yokitoki.zakupy.mpp.feature.roster.presentation.RosterViewModel

class RosterListActivity : MvvmActivity<ActivityRosterListBinding, RosterViewModel<Roster>>() {
    override val layoutId: Int = R.layout.activity_roster_list
    @Suppress("UNCHECKED_CAST")
    override val viewModelClass = RosterViewModel::class.java as Class<RosterViewModel<Roster>>
    override val viewModelVariableId: Int = BR.viewModel

    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {
        AppComponent.factory.rosterFactory.createListViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding.refreshLayout) {
            setOnRefreshListener {
                viewModel.onRefresh { isRefreshing = false }
            }
        }
    }
}
