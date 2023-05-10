package com.kevin.netkick.network.model.news

data class NewsResponseModel(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleModel?>?
){
    companion object{
        fun transformsToEntity():{

        }
    }
}
