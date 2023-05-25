package com.kevin.netkick.network.model.player

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.player.ResponseP
import com.kevin.netkick.domain.entity.player.subplayer.Player
import com.kevin.netkick.network.model.player.subplayer.BirthModel
import com.kevin.netkick.network.model.player.subplayer.PlayerModel
import com.kevin.netkick.network.model.player.substatistic.StatisticModel

data class ResponsePModel(
    @SerializedName("player")
    val players: PlayerModel?,
    @SerializedName("statistics")
    val statistics: List<StatisticModel>?
) {
    companion object {
        fun transformToListEntity(item: List<ResponsePModel>): List<ResponseP> {
            return item.map {
                transformToEntity(it)
            }
        }

        private fun transformToEntity(it: ResponsePModel): ResponseP {
            return ResponseP(
                players = PlayerModel.transformToEntity(
                    it.players ?: PlayerModel(
                        id = 0,
                        name = "",
                        firstname = "",
                        lastname = "",
                        age = 0,
                        birth = BirthModel("", "", ""),
                        nationality = "",
                        height = "",
                        weight = "",
                        photo = ""
                    )
                ),
                statistics = StatisticModel.transformToListEntity(it.statistics ?: listOf())
            )
        }
    }
}
