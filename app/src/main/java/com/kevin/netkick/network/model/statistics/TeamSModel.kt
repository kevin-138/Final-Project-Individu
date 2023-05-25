package com.kevin.netkick.network.model.statistics

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.statistics.TeamS

data class TeamSModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("logo")
    val logo: String?
) {
    companion object {
        fun transformToEntity(it: TeamSModel): TeamS {
            return TeamS(
                id = it.id ?: 0,
                name = it.name ?: "",
                logo = it.logo ?: ""
            )
        }
    }
}

