package com.kevin.netkick.network.model.standings.substandings

import com.kevin.netkick.domain.entity.standings.substandings.GoalsSt

data class AllModel(
    val draw: Int?,
    val goals: GoalsSt?,
    val lose: Int?,
    val played: Int?,
    val win: Int?
)
