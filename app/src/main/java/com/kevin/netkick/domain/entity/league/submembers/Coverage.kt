package com.kevin.netkick.domain.entity.league.submembers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coverage(
    val fixtures: LeagueFixture,
    val standings: Boolean
) : Parcelable
