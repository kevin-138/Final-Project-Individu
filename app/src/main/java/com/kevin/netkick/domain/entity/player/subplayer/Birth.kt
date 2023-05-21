package com.kevin.netkick.domain.entity.player.subplayer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Birth(
    val date: String,
    val place: String,
    val country: String,
) : Parcelable
