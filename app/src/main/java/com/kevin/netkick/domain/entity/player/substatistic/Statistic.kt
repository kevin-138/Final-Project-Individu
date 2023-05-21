package com.kevin.netkick.domain.entity.player.substatistic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Statistic(
    val team: TeamP,
    val league: LeagueP,
    val games: GamesP,
    val goals: GoalsP,
) : Parcelable
