package com.kevin.netkick.network.model.player.subplayer

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.player.subplayer.Player

data class PlayerModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("birth")
    val birth: BirthModel?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("height")
    val height: String?,
    @SerializedName("weight")
    val weight: String?,
    @SerializedName("photo")
    val photo: String?
) {
    companion object {
        fun transformToEntity(it: PlayerModel): Player {
            return Player(
                id = it.id ?: 0,
                name = it.name ?: "",
                firstname = it.firstname ?: "",
                lastname = it.lastname ?: "",
                age = it.age ?: 0,
                birth = BirthModel.transformToEntity(it.birth ?: BirthModel("", "", "")),
                nationality = it.nationality ?: "",
                height = it.height ?: "",
                weight = it.weight ?: "",
                photo = it.photo ?: ""
            )
        }
    }
}
