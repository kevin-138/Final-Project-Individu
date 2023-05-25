package com.kevin.netkick.network.model.league.submembers

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.league.submembers.LeagueFixture

data class LeagueFixtureModel(
    @SerializedName("events")
    val events: Boolean?,
    @SerializedName("lineups")
    val lineups: Boolean?,
    @SerializedName("statistics_fixtures")
    val statistic: Boolean?
) {
    companion object {
        fun transformToEntity(it: LeagueFixtureModel): LeagueFixture {
            return LeagueFixture(
                events = it.events ?: false,
                lineups = it.lineups ?: false,
                statistic = it.statistic ?: false
            )
        }
    }
}