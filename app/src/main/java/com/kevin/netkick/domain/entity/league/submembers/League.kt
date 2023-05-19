package com.kevin.netkick.domain.entity.league.submembers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class League(
    val id: Int,
    val name: String,
    val type: String,
    val logo: String
) : Parcelable
