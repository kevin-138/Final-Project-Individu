package com.kevin.netkick.domain.entity.news

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val url: String,
    val publishedAt: String
)
