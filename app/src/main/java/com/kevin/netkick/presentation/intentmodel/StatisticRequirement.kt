package com.kevin.netkick.presentation.intentmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticRequirement(
    val leagueName: String,
    val leagueLogo: String,
    val season: Int,
    val round: String
) : Parcelable
