package com.kevin.netkick.network.model.coach

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.coach.TeamC

data class TeamCModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("logo")
    val logo: String?
) {
    companion object {
        fun transformToEntity(it: TeamCModel): TeamC {
            return TeamC(
                id = it.id ?: 0,
                name = it.name ?: "",
                logo = it.logo ?: ""
            )
        }
    }
}
