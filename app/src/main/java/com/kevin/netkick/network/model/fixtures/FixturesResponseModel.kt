package com.kevin.netkick.network.model.fixtures

import PagingModel
import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse

data class FixturesResponseModel(
    @SerializedName("paging")
    val paging: PagingModel?,
    @SerializedName("results")
    val results: Int?,
    @SerializedName("response")
    val response: List<ResponseFModel?>?
) {
    companion object {
        fun transformToEntity(it: FixturesResponseModel): FixturesResponse {
            return FixturesResponse(
                paging = PagingModel.transformsToEntity(it.paging ?: PagingModel(0, 0)),
                results = it.results ?: 0,
                response = ResponseFModel.transformToListEntity(it.response ?: listOf())
            )
        }
    }
}
