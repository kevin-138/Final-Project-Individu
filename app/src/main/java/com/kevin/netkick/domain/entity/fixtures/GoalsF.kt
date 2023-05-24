package com.kevin.netkick.domain.entity.fixtures

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoalsF(
    val home: Int,
    val away: Int
) : Parcelable
