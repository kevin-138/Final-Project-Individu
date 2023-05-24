package com.kevin.netkick.domain.entity.fixtures

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeagueF(
    val id: Int,
    val name: String,
    val country: String,
    val logo: String,
    val season: Int,
    val round: String
) : Parcelable
