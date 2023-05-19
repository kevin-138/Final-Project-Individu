package com.kevin.netkick.domain.entity.league

import android.os.Parcelable
import com.kevin.netkick.domain.entity.league.submembers.League
import com.kevin.netkick.domain.entity.league.submembers.Season
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseL(
    val league: League,
    val seasons: List<Season>
) : Parcelable
