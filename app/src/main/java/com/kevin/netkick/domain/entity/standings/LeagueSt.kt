package com.kevin.netkick.domain.entity.standings

data class LeagueSt(
    val id: Int,
    val name: String,
    val country: String,
    val logo: String,
    val season: Int,
    val standings: List<Group>
)
