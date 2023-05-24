package com.kevin.netkick.domain.entity.fixtures.subscore

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubScore(
    val home: Int,
    val away: Int
) : Parcelable
