package com.kevin.netkick.domain.entity.fixtures.subteams

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamsSubF(
    val id: Int,
    val name: String,
    val logo: String,
    val winner: Boolean
) : Parcelable
