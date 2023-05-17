package com.kevin.netkick.network.model.standings.substandings

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.standings.substandings.GoalsSt

data class GoalsStModel(
    @SerializedName("against")
    val goalsAgainst: Int?,
    @SerializedName("for")
    val goalsFor: Int?
){
    companion object{
        fun transformToEntity(it:GoalsStModel):GoalsSt {
            return GoalsSt(
                goalsAgainst = it.goalsAgainst ?: 0,
                goalsFor = it.goalsFor ?: 0
            )
        }
    }
}
