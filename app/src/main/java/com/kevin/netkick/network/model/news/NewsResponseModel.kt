package com.kevin.netkick.network.model.news

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.news.NewsResponse

data class NewsResponseModel(
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("articles")
    val articles: List<ArticleModel?>?
) {
    companion object {
        fun transformsToEntity(it: NewsResponseModel): NewsResponse {
            return NewsResponse(
                status = it.status ?: "",
                totalResults = it.totalResults ?: 0,
                articles = ArticleModel.transformToListEntity(it.articles ?: listOf())
            )
        }
    }
}
