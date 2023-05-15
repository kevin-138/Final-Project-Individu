package com.kevin.netkick.network.model.player.substatistic

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.player.substatistic.GamesP

data class GamesPModel(
    @SerializedName("appearences")
    val appearances: Int?,
    @SerializedName("lineups")
    val lineups: Int?,
    @SerializedName("minutes")
    val minutes: Int?,
    @SerializedName("position")
    val position: String?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("captain")
    val captain: Boolean?
){
    companion object{
        fun transformToEntity(it: GamesPModel):GamesP{
            return GamesP(
                appearances = it.appearances ?: 0,
                lineups = it.lineups ?: 0,
                minutes = it.minutes ?: 0,
                position = it.position ?: "",
                rating = it.rating ?: "",
                captain = it.captain ?: false
            )
        }
    }
}
