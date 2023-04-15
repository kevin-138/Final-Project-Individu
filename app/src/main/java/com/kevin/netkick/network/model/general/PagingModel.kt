package com.kevin.netkick.domain.entity.general

import com.google.gson.annotations.SerializedName

data class PagingModel(
    @SerializedName("current")
    val current: Int,
    @SerializedName("total")
    val total: Int
){
    companion object{
        fun transformsToEntity(it: PagingModel):Paging{
            return Paging(
                current = it.current,
                total = it.total
            )
        }
    }
}
