package com.kevin.netkick.domain.entity.teams

data class Team(
    val id: Int,
    val name: String,
    val code: String,
    val country: String,
    val founded: Int,
    val logo: String,
    val national: Boolean
)
