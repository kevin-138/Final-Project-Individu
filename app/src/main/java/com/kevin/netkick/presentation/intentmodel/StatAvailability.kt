package com.kevin.netkick.presentation.intentmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatAvailability(
    val events: Boolean,
    val lineups: Boolean,
    val statistic: Boolean,
    val standings: Boolean
) : Parcelable
