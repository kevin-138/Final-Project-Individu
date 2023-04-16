package com.kevin.netkick.network

import com.kevin.netkick.domain.DomainRepository
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.network.model.fixtures.FixturesResponseModel
import com.kevin.netkick.network.service.FootballApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NetworkDataRepositoryImpl @Inject constructor(private val footballApi:FootballApiService):DomainRepository {

    override suspend fun getLiveMatches(live: String): Flow<FixturesResponse> {
        return flow {
        try {
           val response = footballApi.getLiveMatches(live)
           emit(FixturesResponseModel.transformToEntity(response))
        } catch (e: Throwable){
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)
}


}