package com.kevin.netkick.domain.entity.player.substatistic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamP(
    val id: Int,
    val name: String,
    val logo: String
) : Parcelable
