package com.kevin.netkick.network.model.league

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.league.ResponseL
import com.kevin.netkick.network.model.league.submembers.LeagueModel
import com.kevin.netkick.network.model.league.submembers.SeasonModel

data class ResponseLModel(
    @SerializedName("league")
    val league: LeagueModel?,
    @SerializedName("seasons")
    val seasons: List<SeasonModel>?
) {
    companion object{
        fun transformToListEntity(item: List<ResponseLModel>):List<ResponseL> {
            return item.map {
                transformToEntity(it)
            }
        }

        fun transformToEntity (it: ResponseLModel): ResponseL{
            return ResponseL(
                league = LeagueModel.transformToEntity(it.league ?: LeagueModel(0,"","","")),
                seasons = SeasonModel.transformToEntityList(it.seasons ?: listOf())
            )
        }
    }
}
