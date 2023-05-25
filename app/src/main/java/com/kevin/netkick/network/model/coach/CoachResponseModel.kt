package com.kevin.netkick.network.model.coach

import PagingModel
import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.coach.CoachResponse

data class CoachResponseModel(
    @SerializedName("paging")
    val paging: PagingModel?,
    @SerializedName("results")
    val results: Int?,
    @SerializedName("response")
    val response: List<ResponseCModel>?
) {
    companion object {
        fun transformToEntity(it: CoachResponseModel): CoachResponse {
            return CoachResponse(
                paging = PagingModel.transformsToEntity(it.paging ?: PagingModel(0, 0)),
                results = it.results ?: 0,
                response = ResponseCModel.transformToListEntity(it.response ?: listOf()),
                error = ""
            )
        }
    }
}
