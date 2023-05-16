package com.kevin.netkick.network.model.league.submembers

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.league.submembers.Coverage

data class CoverageModel (
    @SerializedName("fixtures")
    val fixtures: LeagueFixtureModel?,
    @SerializedName("standings")
    val standings: Boolean?
){
    companion object{
        fun transformToEntity(it: CoverageModel):Coverage{
            return Coverage(
                fixtures = LeagueFixtureModel.transformToEntity(it.fixtures ?: LeagueFixtureModel(events = false,lineups = false,statistic = false)),
                standings = it.standings ?: false
            )
        }
    }
}