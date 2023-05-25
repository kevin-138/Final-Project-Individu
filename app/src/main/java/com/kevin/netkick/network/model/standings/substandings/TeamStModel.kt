package com.kevin.netkick.network.model.standings.substandings

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.standings.substandings.TeamSt

data class TeamStModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("logo")
    val logo: String?
) {
    companion object {
        fun transformToEntity(it: TeamStModel): TeamSt {
            return TeamSt(
                id = it.id ?: 0,
                name = it.name ?: "",
                logo = it.logo ?: ""
            )
        }
    }
}