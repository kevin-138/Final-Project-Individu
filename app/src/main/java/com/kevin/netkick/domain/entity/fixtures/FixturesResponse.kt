package com.kevin.netkick.domain.entity.fixtures

import com.kevin.netkick.domain.entity.general.Paging

data class FixturesResponse(
    val paging: Paging,
    val results: Int,
    val response: List<ResponseF>
)
