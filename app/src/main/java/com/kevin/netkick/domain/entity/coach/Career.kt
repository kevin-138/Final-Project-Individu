package com.kevin.netkick.domain.entity.coach

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Career(
    val start: String,
    val end: String,
    val team: TeamC
) : Parcelable
