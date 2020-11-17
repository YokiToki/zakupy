package com.github.yokitoki.zakupy.android.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.yokitoki.zakupy.android.AppComponent
import dev.icerock.moko.mvvm.MvvmActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import com.github.yokitoki.zakupy.android.R
import com.github.yokitoki.zakupy.android.BR
import com.github.yokitoki.zakupy.android.databinding.ActivityMainListBinding
import com.github.yokitoki.zakupy.mpp.domain.entity.MainListItem
import com.github.yokitoki.zakupy.mpp.feature.mainlist.presentation.ListViewModel

class MainListActivity : MvvmActivity<ActivityMainListBinding, ListViewModel<MainListItem>>() {
    override val layoutId: Int = R.layout.activity_main_list
    @Suppress("UNCHECKED_CAST")
    override val viewModelClass = ListViewModel::class.java as Class<ListViewModel<MainListItem>>
    override val viewModelVariableId: Int = BR.viewModel

    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {
        AppComponent.factory.listFactory.createListViewModel()
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
