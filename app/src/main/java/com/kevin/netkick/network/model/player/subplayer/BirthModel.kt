package com.kevin.netkick.network.model.player.subplayer

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.player.subplayer.Birth

data class BirthModel(
    @SerializedName("date")
    val date: String?,
    @SerializedName("place")
    val place: String?,
    @SerializedName("country")
    val country: String?
){
    companion object{
        fun transformToEntity(it:BirthModel?):Birth {
            return Birth(
                date = it?.date ?: "",
                place = it?.place ?: "",
                country = it?.country ?: ""
            )
        }
    }
}
