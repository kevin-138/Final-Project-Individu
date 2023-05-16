package com.kevin.netkick.domain.entity.league

import com.kevin.netkick.domain.entity.league.submembers.League
import com.kevin.netkick.domain.entity.league.submembers.Season

data class ResponseL(
    val league: League,
    val seasons: List<Season>
)
