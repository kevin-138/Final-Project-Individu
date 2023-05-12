package com.kevin.netkick.network.model.news

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.news.Source

data class SourceModel(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
){
    companion object{
        fun transformsToEntity(it:SourceModel):Source{
            return Source(
                id = it.id ?: "",
                name = it.name ?: ""
            )
        }
    }
}
