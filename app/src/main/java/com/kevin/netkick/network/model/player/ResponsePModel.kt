package com.kevin.netkick.network.model.player

import com.kevin.netkick.domain.entity.player.ResponseP
import com.kevin.netkick.network.model.player.subplayer.PlayerModel
import com.kevin.netkick.network.model.player.substatistic.StatisticModel

data class ResponsePModel(
    val players:PlayerModel,
    val statistics: List<StatisticModel>
){
    companion object{
        fun transformToListEntity(item:List<ResponsePModel>):List<ResponseP>{
            return item.map {
                transformToEntity(it)
            }
        }
        private fun transformToEntity(it: ResponsePModel):ResponseP{
            return ResponseP(
                players = PlayerModel.transformToEntity(it.players),
                statistics =  StatisticModel.transformToListEntity(it.statistics)
            )
        }
    }
}
