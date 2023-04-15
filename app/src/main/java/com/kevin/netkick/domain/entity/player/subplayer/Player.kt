package com.kevin.netkick.domain.entity.player.subplayer

data class Player(
    val id: Int,
    val name: String,
    val firstname: String,
    val lastname: String,
    val age: Int,
    val birth: Birth,
    val nationality: String,
    val height: String,
    val weight: String,
    val photo: String
)
