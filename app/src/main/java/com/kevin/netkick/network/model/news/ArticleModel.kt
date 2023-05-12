package com.kevin.netkick.network.model.news

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.news.Article

data class ArticleModel(
    @SerializedName("source")
    val source: SourceModel?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?
){
    companion object{
        fun transformToListEntity(item: List<ArticleModel?>):List<Article>{
            return item.map {
                    transformToEntity(it ?: ArticleModel(
                        source = SourceModel("",""),
                        author = "",
                        title = "",
                        url = "",
                        publishedAt = ""
                    ))
            }
        }

        fun transformToEntity(it: ArticleModel):Article{
            return Article(
                source = SourceModel.transformsToEntity(it.source ?: SourceModel("","")),
                author = it.author ?: "",
                title = it.title ?: "",
                url = it.url ?: "",
                publishedAt = it.publishedAt ?: ""
            )
        }
    }
}
