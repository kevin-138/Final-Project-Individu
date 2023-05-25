package com.kevin.netkick.network.model.league

import PagingModel
import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.league.LeagueResponse

data class LeagueResponseModel(
    @SerializedName("paging")
    val paging: PagingModel?,
    @SerializedName("results")
    val results: Int?,
    @SerializedName("response")
    val response: List<ResponseLModel>?
) {
    companion object {
        fun transformToEntity(it: LeagueResponseModel): LeagueResponse {
            return LeagueResponse(
                results = it.results ?: 0,
                paging = PagingModel.transformsToEntity(it.paging ?: PagingModel(0, 0)),
                response = ResponseLModel.transformToListEntity(it.response ?: listOf()),
                error = ""
            )
        }
    }
}
