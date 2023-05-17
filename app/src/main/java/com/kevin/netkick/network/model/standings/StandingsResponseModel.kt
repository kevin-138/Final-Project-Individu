package com.kevin.netkick.network.model.standings

import PagingModel
import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.standings.StandingsResponse

data class StandingsResponseModel(
    @SerializedName("paging")
    val paging: PagingModel?,
    @SerializedName("results")
    val results: Int?,
    @SerializedName("response")
    val response: List<ResponseStModel>?
){
    companion object{
        fun transformToEntity(it:StandingsResponseModel):StandingsResponse{
            return StandingsResponse(
                paging = PagingModel.transformsToEntity(it.paging ?: PagingModel(0,0)),
                results = it.results ?: 0,
                response = ResponseStModel.transformToListEntity(it.response ?: listOf())
            )
        }
    }
}
