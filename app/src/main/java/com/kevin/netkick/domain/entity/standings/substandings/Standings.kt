package com.kevin.netkick.domain.entity.standings.substandings

data class Standings(
    val rank: Int,
    val team: TeamSt,
    val points: Int,
    val goalsDiff: Int,
    val group: String,
    val description: String,
    val all: All
)
