package com.kevin.netkick.domain.entity.teams

import com.kevin.netkick.domain.entity.general.Paging

data class TeamResponse(
    val paging: Paging,
    val results: Int,
    val response: List<ResponseT>,
    val error: String
)
