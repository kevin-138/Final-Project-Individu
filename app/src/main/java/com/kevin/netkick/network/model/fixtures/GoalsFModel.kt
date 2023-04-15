package com.kevin.netkick.network.model.fixtures

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.GoalsF

data class GoalsFModel(
    @SerializedName("home")
    val home: Int?,
    @SerializedName("away")
    val away: Int?
){
    companion object{
        fun transformToEntity(it:GoalsFModel):GoalsF{
            return GoalsF(
                0,0
            )
        }
    }
}

