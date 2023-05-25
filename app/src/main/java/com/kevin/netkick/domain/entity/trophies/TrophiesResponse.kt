package com.kevin.netkick.domain.entity.trophies

import com.kevin.netkick.domain.entity.general.Paging

data class TrophiesResponse(
    val paging: Paging,
    val results: Int,
    val response: List<ResponseTrop>,
    val error: String
)
