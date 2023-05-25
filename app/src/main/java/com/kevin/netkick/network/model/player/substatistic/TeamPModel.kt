package com.kevin.netkick.network.model.player.substatistic

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.player.substatistic.TeamP

data class TeamPModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("logo")
    val logo: String?
) {
    companion object {
        fun transformToEntity(it: TeamPModel): TeamP {
            return TeamP(
                id = it.id ?: 0,
                name = it.name ?: "",
                logo = it.logo ?: ""
            )
        }
    }
}
