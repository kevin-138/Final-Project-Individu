package com.kevin.netkick.network.model.fixtures

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.LeagueF

data class LeagueFModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("season")
    val season: Int?,
    @SerializedName("round")
    val round: String?
) {
    companion object {
        fun transformToEntity(it: LeagueFModel): LeagueF {
            return LeagueF(
                id = it.id ?: 0,
                name = it.name ?: "",
                country = it.country ?: "",
                logo = it.logo ?: "",
                season = it.season ?: 0,
                round = it.round ?: ""
            )
        }
    }
}
