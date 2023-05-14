package com.kevin.netkick.network.model.player

import com.kevin.netkick.network.model.player.subplayer.PlayerModel
import com.kevin.netkick.network.model.player.substatistic.StatisticModel

data class ResponsePModel(
    val players:PlayerModel,
    val statistics: List<StatisticModel>
){
    companion object{
        fun transformToEntity()
    }
}
