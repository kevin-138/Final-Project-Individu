package com.kevin.netkick.domain.entity.fixtures.subscore

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
    val halftime: SubScore,
    val fulltime: SubScore
) : Parcelable
