package com.kevin.netkick.domain.entity.teams

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseT(
    val team: Team,
    val venue: Venue
) : Parcelable
