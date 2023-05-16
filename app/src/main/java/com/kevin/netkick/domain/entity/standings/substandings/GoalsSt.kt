package com.kevin.netkick.domain.entity.standings.substandings

import com.google.gson.annotations.SerializedName

data class GoalsSt(
    @SerializedName("")
    val goalsAgainst: Int?,
    @SerializedName("")
    val goalsFor: Int?
){
    companion object{

    }
}