package com.kevin.netkick.network.model.player.substatistic

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.player.substatistic.GoalsP

data class GoalsPModel(
    @SerializedName("total")
    val total: Int?,
    @SerializedName("assists")
    val assists: Int?,
    @SerializedName("conceded")
    val conceded: Int?,
    @SerializedName("saves")
    val saves: Int?,
){
    companion object{
        fun transformToEntity(it:GoalsPModel):GoalsP{
            return GoalsP(
                total = it.total ?: 0,
                assists = it.assists ?: 0,
                conceded = it.conceded ?: 0,
                saves = it.saves ?: 0
            )
        }
    }
}
