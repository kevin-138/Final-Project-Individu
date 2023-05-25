package com.kevin.netkick.network.model.trophies

import PagingModel
import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.trophies.TrophiesResponse

data class TrophiesResponseModel(
    @SerializedName("paging")
    val paging: PagingModel?,
    @SerializedName("results")
    val results: Int?,
    @SerializedName("response")
    val response: List<ResponseTropModel>?
) {
    companion object {
        fun transformToEntity(it: TrophiesResponseModel): TrophiesResponse {
            return TrophiesResponse(
                paging = PagingModel.transformsToEntity(it.paging ?: PagingModel(0, 0)),
                results = it.results ?: 0,
                response = ResponseTropModel.transformToListEntity(it.response ?: listOf()),
                error = ""
            )
        }
    }
}
