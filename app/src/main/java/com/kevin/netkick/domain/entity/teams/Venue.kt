package com.kevin.netkick.domain.entity.teams

data class Venue(
    var id: Int,
    var name: String,
    var address: String,
    var city: String,
    var capacity: Int,
    var surface: String,
    var image: String
)
