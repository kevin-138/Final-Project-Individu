package com.kevin.netkick.domain.entity.league.submembers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Season(
    val year: String,
    val start: String,
    val end: String,
    val coverage: Coverage
) : Parcelable
