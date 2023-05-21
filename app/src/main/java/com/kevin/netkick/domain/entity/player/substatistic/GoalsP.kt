package com.kevin.netkick.domain.entity.player.substatistic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoalsP (
        val total: Int,
        val assists: Int,
        val conceded: Int,
        val saves: Int,
        ) : Parcelable