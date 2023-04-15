package com.kevin.netkick.network.model.fixtures.subteams

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.subteams.TeamsF

data class TeamsFModel(
    @SerializedName("home")
    val homeTeam: TeamsSubFModel?,
    @SerializedName("away")
    val awayTeam: TeamsSubFModel?
){
    companion object{
        fun transformToEntity(it: TeamsFModel):TeamsF{
            return TeamsF(
                homeTeam = TeamsSubFModel.transformToEntity(it.homeTeam ?: TeamsSubFModel(0,"","",false)),
                awayTeam = TeamsSubFModel.transformToEntity(it.awayTeam ?: TeamsSubFModel(0,"","",false))
            )
        }
    }
}
