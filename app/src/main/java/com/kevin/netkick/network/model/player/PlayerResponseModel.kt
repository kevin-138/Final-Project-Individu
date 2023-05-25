package com.kevin.netkick.network.model.player

import PagingModel
import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.player.PlayerResponse

data class PlayerResponseModel(
    @SerializedName("paging")
    val paging: PagingModel?,
    @SerializedName("results")
    val results: Int?,
    @SerializedName("response")
    val response: List<ResponsePModel>?
) {
    companion object {
        fun transformToEntity(it: PlayerResponseModel): PlayerResponse {
            return PlayerResponse(
                results = it.results ?: 0,
                paging = PagingModel.transformsToEntity(it.paging ?: PagingModel(0, 0)),
                response = ResponsePModel.transformToListEntity(it.response ?: listOf()),
                error = ""
            )
        }
    }
}
