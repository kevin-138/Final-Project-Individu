package com.kevin.netkick.network.model.rounds

import PagingModel
import com.kevin.netkick.domain.entity.rounds.RoundsResponse
import com.kevin.netkick.network.model.standings.ResponseStModel

data class RoundsResponseModel(
    val paging: PagingModel?,
    val results: Int?,
    val response: List<String>?
){
    companion object{
        fun transformToEntity(it: RoundsResponseModel):RoundsResponse{
            return RoundsResponse(
                paging = PagingModel.transformsToEntity(it.paging ?: PagingModel(0,0)),
                results = it.results ?: 0,
                response = it.response ?: listOf()
            )
        }
    }
}
