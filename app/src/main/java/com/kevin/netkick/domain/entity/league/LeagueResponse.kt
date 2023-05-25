package com.kevin.netkick.domain.entity.league

import com.kevin.netkick.domain.entity.general.Paging

data class LeagueResponse(
    val paging: Paging,
    val results: Int,
    val response: List<ResponseL>,
    val error: String
)
