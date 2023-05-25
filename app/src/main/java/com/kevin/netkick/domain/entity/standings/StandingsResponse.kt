package com.kevin.netkick.domain.entity.standings

import com.kevin.netkick.domain.entity.general.Paging

data class StandingsResponse(
    val paging: Paging,
    val results: Int,
    val response: List<ResponseSt>,
    val error: String
)
