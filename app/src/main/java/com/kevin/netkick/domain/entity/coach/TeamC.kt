package com.kevin.netkick.domain.entity.coach

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamC(
    val id: Int,
    val name: String,
    val logo: String
) : Parcelable
