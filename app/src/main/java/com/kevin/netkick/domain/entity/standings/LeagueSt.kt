package com.kevin.netkick.domain.entity.standings

import com.kevin.netkick.domain.entity.standings.substandings.Standings

data class LeagueSt(
    val id: Int,
    val name: String,
    val country: String,
    val logo: String,
    val season: Int,
    val standings: List<List<Standings>>
)
