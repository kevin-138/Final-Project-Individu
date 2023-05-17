package com.kevin.netkick.network.model.standings

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.standings.ResponseSt

data class ResponseStModel(
    val league: LeagueStModel?
){
    companion object{
        fun transformToListEntity(item:List<ResponseStModel>):List<ResponseSt>{
            return item.map{
                transformToEntity(it)
            }
        }
        fun transformToEntity(it:ResponseStModel):ResponseSt{
            return ResponseSt(
                league = LeagueStModel.transformToEntity(it.league ?: LeagueStModel(
                    0,"","","",0, listOf()
                )
                )
            )
        }
    }
}
