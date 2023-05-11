package com.kevin.netkick.network.model.news

import com.kevin.netkick.domain.entity.news.NewsResponse

data class NewsResponseModel(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleModel?>?
){
    companion object{
        fun transformsToEntity(it: NewsResponseModel): NewsResponse{
                return NewsResponse(
                    status = it.status ?: "",
                    totalResults = it.totalResults ?: 0,
                    articles = ArticleModel.transformToListEntity(it.articles ?: listOf())
                )
        }
    }
}
