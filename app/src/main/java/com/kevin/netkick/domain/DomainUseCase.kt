package com.kevin.netkick.domain

import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.domain.entity.teams.TeamResponse
import kotlinx.coroutines.flow.Flow

interface DomainUseCase {
    suspend fun getLiveMatches(live:String): Flow<FixturesResponse>
    suspend fun getPopularTeamsHome(league:String, season:String): Flow<TeamResponse>

}