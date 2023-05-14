package com.kevin.netkick.network.model.player.substatistic

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.player.substatistic.Statistic

data class StatisticModel(
    @SerializedName("team")
    val team: TeamPModel,
    @SerializedName("league")
    val league: LeaguePModel,
    @SerializedName("games")
    val games: GamesPModel,
    @SerializedName("goals")
    val goals: GoalsPModel
){
    companion object{
        fun transformToListEntity(item: List<StatisticModel>):List<Statistic>{
            return item.map {
                transformToEntity(it)
            }
        }
        fun transformToEntity(it:StatisticModel):Statistic{
            return Statistic(
                TeamPModel.transformToEntity(it.team),
                LeaguePModel.transformToEntity(it.league),
                GamesPModel.transformToEntity(it.games),
                GoalsPModel.transformToEntity(it.goals)
            )
        }
    }
}
