package com.kevin.netkick.domain.entity.news

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
