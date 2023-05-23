package com.kevin.netkick.network.model.statistics

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.statistics.ResponseS

data class ResponseSModel(
    @SerializedName("team")
    val team: TeamSModel?,
    @SerializedName("statistics")
    val statistics: List<StatsModel>?
){
    companion object{
        fun transformToListEntity(item: List<ResponseSModel>):List<ResponseS>{
            return item.map {
                transformToEntity(it)
            }
        }
        fun transformToEntity(it: ResponseSModel): ResponseS {
            return ResponseS(
                team = TeamSModel.transformToEntity(it.team ?: TeamSModel(0,"","")),
            statistics = StatsModel.transformToListEntity(it.statistics ?: listOf())
            )
        }
    }
}
