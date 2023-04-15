package com.kevin.netkick.domain

import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import kotlinx.coroutines.flow.Flow

interface DomainRepository {
    suspend fun getLiveMatches(live:String): Flow<FixturesResponse>

}