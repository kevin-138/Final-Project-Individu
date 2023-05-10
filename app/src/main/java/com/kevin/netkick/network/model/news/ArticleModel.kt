package com.kevin.netkick.network.model.news

import com.kevin.netkick.domain.entity.news.Article

data class ArticleModel(
    val source: SourceModel?,
    val author: String?,
    val title: String?,
    val url: String?,
    val publishedAt: String?
){
    companion object{
        fun transformToListEntity(item: List<ArticleModel?>):List<Article>{
            return item.map {

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
