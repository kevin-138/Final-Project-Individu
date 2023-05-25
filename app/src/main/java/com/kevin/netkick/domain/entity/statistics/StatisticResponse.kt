package com.kevin.netkick.domain.entity.statistics

import com.kevin.netkick.domain.entity.general.Paging
import com.kevin.netkick.domain.entity.player.ResponseP

data class StatisticResponse (
    val paging: Paging,
    val results: Int,
    val response: List<ResponseS>,
    val error: String
)