package com.kevin.netkick.network.model.statistics

import PagingModel
import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.statistics.StatisticResponse

data class StatisticResponseModel(
    @SerializedName("paging")
    val paging: PagingModel?,
    @SerializedName("results")
    val results: Int?,
    @SerializedName("response")
    val response: List<ResponseSModel>?
) {
    companion object {
        fun transformToEntity(it: StatisticResponseModel): StatisticResponse {
            return StatisticResponse(
                paging = PagingModel.transformsToEntity(it.paging ?: PagingModel(0, 0)),
                results = it.results ?: 0,
                response = ResponseSModel.transformToListEntity(it.response ?: listOf()),
                error = ""
            )
        }
    }
}
