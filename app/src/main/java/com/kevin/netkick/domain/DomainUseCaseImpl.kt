package com.kevin.netkick.domain

import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.domain.entity.teams.TeamResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DomainUseCaseImpl @Inject constructor(private val repository: DomainRepository):DomainUseCase  {
    override suspend fun getLiveMatches(live: String): Flow<FixturesResponse> {
        return repository.getLiveMatches(live)
    }

    override suspend fun getPopularTeamsHome(league: String, season: String): Flow<TeamResponse> {
        return repository.getPopularTeamsHome(league,season)
    }

}