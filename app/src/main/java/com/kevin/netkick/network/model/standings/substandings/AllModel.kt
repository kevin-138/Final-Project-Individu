package com.kevin.netkick.network.model.standings.substandings

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.standings.substandings.All

data class AllModel(
    @SerializedName("draw")
    val draw: Int?,
    @SerializedName("goals")
    val goals: GoalsStModel?,
    @SerializedName("lose")
    val lose: Int?,
    @SerializedName("played")
    val played: Int?,
    @SerializedName("win")
    val win: Int?
){
    companion object{
        fun transformToEntity(it:AllModel):All{
            return All(
                draw = it.draw ?: 0,
                goals = GoalsStModel.transformToEntity(it.goals ?: GoalsStModel(0,0)),
                lose = it.lose ?: 0,
                played = it.played ?: 0,
                win = it.win ?: 0
            )
        }
    }
}
