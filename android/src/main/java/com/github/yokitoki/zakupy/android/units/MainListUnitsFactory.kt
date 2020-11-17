package com.github.yokitoki.zakupy.android.units

import com.github.yokitoki.zakupy.android.MainListItem
import dev.icerock.moko.units.TableUnitItem
import com.github.yokitoki.zakupy.mpp.SharedFactory
import com.github.yokitoki.zakupy.mpp.domain.entity.MainListVisibility

class MainListUnitsFactory : SharedFactory.MainListUnitsFactory {
    override fun createMainListItem(
        id: Long,
        name: String,
        visibility: MainListVisibility
    ): TableUnitItem {
        return MainListItem().apply {
            itemId = id
            this.name = name
            this.visibility = when (visibility) {
                MainListVisibility.PERSONAL -> "personal"
                MainListVisibility.SHARED -> "shared"
            }
        }
    }
}
