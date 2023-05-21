package com.kevin.netkick.network.model.coach

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.coach.Career

data class CareerModel(
    @SerializedName("start")
    val start: String?,
    @SerializedName("end")
    val end: String?,
    @SerializedName("team")
    val team: TeamCModel?
){
    companion object{
        fun transformToListEntity(item:List<CareerModel>):List<Career>{
            return item.map {
                transformToEntity(it)
            }
        }

        fun transformToEntity(it:CareerModel):Career{
            return Career(
                start = it.start ?: "-",
                end = it.end ?: "-",
                team = TeamCModel.transformToEntity(it.team ?: TeamCModel(0,"",""))
            )
        }
    }
}
