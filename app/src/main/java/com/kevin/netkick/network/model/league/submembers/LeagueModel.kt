package com.kevin.netkick.network.model.league.submembers

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.league.submembers.League

data class LeagueModel (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("logo")
    val logo: String?
){
    companion object{
        fun transformToEntity(it: LeagueModel):League{
            return League(
                id = it.id ?: 0,
                name = it.name ?: "",
                type = it.type ?: "",
                logo = it.logo ?: ""
            )
        }
    }
}