package com.kevin.netkick.network.model.fixtures.subscore

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.subscore.Score

data class ScoreModel(
    @SerializedName("halftime")
    val halftime: SubScoreModel?,
    @SerializedName("fulltime")
    val fulltime: SubScoreModel?
){
    companion object{
        fun transformToEntity(it:ScoreModel):Score{
            return Score(
                halftime = SubScoreModel.transformToEntity(it.halftime ?: SubScoreModel(0,0)),
                fulltime = SubScoreModel.transformToEntity(it.fulltime ?: SubScoreModel(0,0))
            )
        }
    }
}
