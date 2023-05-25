package com.kevin.netkick.domain.entity.country

import com.kevin.netkick.domain.entity.general.Paging

data class CountryResponse(
    val results: Int,
    val paging: Paging,
    val response: List<CountryC>,
    val error: String
)
