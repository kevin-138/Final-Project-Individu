package com.kevin.netkick.network.model.trophies

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.trophies.ResponseTrop

data class ResponseTropModel(
    @SerializedName("league")
    val league: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("season")
    val season: String?,
    @SerializedName("place")
    val place: String?
){
    companion object {
        fun transformToListEntity(item: List<ResponseTropModel>):List<ResponseTrop>{
            return item.map {
                transformToEntity(it)
            }
        }

        fun transformToEntity(it:ResponseTropModel):ResponseTrop{
            return ResponseTrop(
                league = it.league ?: "",
                country = it.country ?: "",
                season = it.season ?: "",
                place = it.place ?: ""
            )

        }
    }
}
