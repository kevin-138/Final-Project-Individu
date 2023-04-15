package com.kevin.netkick.domain.entity.player

import com.kevin.netkick.domain.entity.player.subplayer.Player
import com.kevin.netkick.domain.entity.player.substatistic.Statistic

data class ResponseP(
    val players: Player,
    val statistics: List<Statistic>
)