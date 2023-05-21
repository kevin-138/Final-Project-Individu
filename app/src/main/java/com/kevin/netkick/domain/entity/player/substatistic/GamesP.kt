package com.kevin.netkick.domain.entity.player.substatistic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GamesP (
        val appearances: Int,
        val lineups: Int,
        val minutes: Int,
        val position: String,
        val rating: String,
        val captain: Boolean
        ) : Parcelable