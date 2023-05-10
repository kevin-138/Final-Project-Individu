package com.kevin.netkick.network.model.news

import com.kevin.netkick.domain.entity.news.Source

data class SourceModel(
    val id: String?,
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
