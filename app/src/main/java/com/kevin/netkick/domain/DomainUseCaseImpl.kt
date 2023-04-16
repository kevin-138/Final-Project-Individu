package com.kevin.netkick.domain

import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DomainUseCaseImpl @Inject constructor(private val repository: DomainRepository):DomainUseCase  {
    override suspend fun getLiveMatches(live: String): Flow<FixturesResponse> {
        return repository.getLiveMatches(live)
    }

}