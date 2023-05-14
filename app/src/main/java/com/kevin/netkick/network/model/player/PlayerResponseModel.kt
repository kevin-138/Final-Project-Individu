package com.kevin.netkick.network.model.player

import PagingModel
import com.google.gson.annotations.SerializedName

data class PlayerResponseModel(
    @SerializedName("paging")
    val paging: PagingModel?,
    @SerializedName("results")
    val results: Int?,
    @SerializedName("response")
    val response: List<ResponsePModel?>?
){
    companion object{

    }
}
