package com.github.yokitoki.zakupy.android.units

import com.github.yokitoki.zakupy.android.Roster
import dev.icerock.moko.units.TableUnitItem
import com.github.yokitoki.zakupy.mpp.SharedFactory
import com.github.yokitoki.zakupy.mpp.domain.entity.RosterVisibility

class RosterUnitsFactory :
    SharedFactory.RosterUnitsFactory {
    override fun createMainListItem(
        id: Long,
        name: String,
        visibility: RosterVisibility
    ): TableUnitItem {
        return Roster().apply {
            itemId = id
            this.name = name
            this.visibility = when (visibility) {
                RosterVisibility.PERSONAL -> "personal"
                RosterVisibility.SHARED -> "shared"
            }
        }
    }
}
