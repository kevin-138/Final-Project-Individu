package com.kevin.netkick.domain.entity.player

import com.kevin.netkick.domain.entity.general.Paging

data class PlayerResponse(
    val paging: Paging,
    val results: Int,
    val response: List<ResponseP>,
)
