package com.kevin.netkick.network.model.fixtures.subscore

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.subscore.SubScore

data class SubScoreModel(
    @SerializedName("home")
    val home: Int?,
    @SerializedName("away")
    val away: Int?
){
    companion object{
        fun transformToEntity(it:SubScoreModel):SubScore{
            return SubScore(
                0,0
            )
        }
    }
}
