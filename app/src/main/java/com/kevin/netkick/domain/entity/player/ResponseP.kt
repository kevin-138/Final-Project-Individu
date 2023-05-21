package com.kevin.netkick.domain.entity.player

import android.os.Parcelable
import com.kevin.netkick.domain.entity.player.subplayer.Player
import com.kevin.netkick.domain.entity.player.substatistic.Statistic
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseP(
    val players: Player,
    val statistics: List<Statistic>
) : Parcelable