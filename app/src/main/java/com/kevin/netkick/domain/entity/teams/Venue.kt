package com.kevin.netkick.domain.entity.teams

data class Venue(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val capacity: Int,
    val surface: String,
    val image: String
)
