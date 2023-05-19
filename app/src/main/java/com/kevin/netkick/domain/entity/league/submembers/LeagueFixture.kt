package com.kevin.netkick.domain.entity.league.submembers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeagueFixture(
    val events: Boolean,
    val lineups: Boolean,
    val statistic: Boolean
) : Parcelable
