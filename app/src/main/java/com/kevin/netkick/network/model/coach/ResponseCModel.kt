package com.kevin.netkick.network.model.coach

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.coach.ResponseC
import com.kevin.netkick.network.model.player.subplayer.BirthModel

data class ResponseCModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("birth")
    val birth: BirthModel?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("team")
    val team: TeamCModel?,
    @SerializedName("career")
    val career: List<CareerModel>?
){companion object{
    fun transformToListEntity(item:List<ResponseCModel>):List<ResponseC>{
        return item.map {
            transformToEntity(it)
        }
    }

    fun transformToEntity(it:ResponseCModel):ResponseC{
        return ResponseC(
            name = it.name ?: "",
            age = it.age ?: 0,
            birth = BirthModel.transformToEntity(it.birth ?: BirthModel("","","")),
            nationality = it.nationality ?: "",
            photo = it.photo ?: "",
            team = TeamCModel.transformToEntity(it.team ?: TeamCModel(0,"","")),
            career = CareerModel.transformToListEntity(it.career ?: listOf())
        )
    }
}
}
