package com.kevin.netkick.domain.entity.coach

import com.kevin.netkick.domain.entity.general.Paging

data class CoachResponse(
    val paging: Paging,
    val results: Int,
    val response: List<ResponseC>
)
