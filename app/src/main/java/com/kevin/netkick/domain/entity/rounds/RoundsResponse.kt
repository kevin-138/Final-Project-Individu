package com.kevin.netkick.domain.entity.rounds

import com.kevin.netkick.domain.entity.general.Paging

data class RoundsResponse(
    val paging: Paging,
    val results: Int,
    val response: List<String>,
)
