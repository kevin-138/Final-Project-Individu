package com.kevin.netkick.network.model.player.substatistic

import com.kevin.netkick.domain.entity.player.substatistic.Statistic

data class StatisticModel(
    val team: TeamPModel,
    val league: LeaguePModel,
    val games: GamesPModel,
    val goals: GoalsPModel
){
    companion object{
        fun transformToEntity(it:StatisticModel):Statistic{
            return Statistic(
                TeamPModel.transformToEntity(it.team),
                LeaguePModel.transformToEntity()
            )
        }
    }
}
