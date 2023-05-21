package com.kevin.netkick.domain.entity.player.substatistic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeagueP(
    val id: Int,
    val name: String,
    val country: String,
    val logo: String,
    val season: Int
) : Parcelable
