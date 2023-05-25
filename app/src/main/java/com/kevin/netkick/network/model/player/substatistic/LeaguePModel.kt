package com.kevin.netkick.network.model.player.substatistic

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.player.substatistic.LeagueP

data class LeaguePModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("season")
    val season: Any?
){
    companion object{
        fun transformToEntity(it:LeaguePModel):LeagueP{
            return LeagueP(
                id = it.id ?: 0,
                name = it.name ?: "",
                country = it.country ?: "",
                logo = it.logo ?: "",
                season = when (it.season){
                    is String -> {
                        it.season
                    }
                    is Int -> {  it.season.toString() }
                    is Double -> {it.season.toString().substringBefore('.'," ")}
                    else -> {"0"}
                }
            )
        }
    }
}
