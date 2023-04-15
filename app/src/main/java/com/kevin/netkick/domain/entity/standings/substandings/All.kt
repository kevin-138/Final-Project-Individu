package com.kevin.netkick.domain.entity.standings.substandings

data class All(
    val draw: Int,
    val goals: GoalsSt,
    val lose: Int,
    val played: Int,
    val win: Int
)
