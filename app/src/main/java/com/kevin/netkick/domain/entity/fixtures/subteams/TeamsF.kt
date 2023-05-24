package com.kevin.netkick.domain.entity.fixtures.subteams

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamsF(
    val homeTeam: TeamsSubF,
    val awayTeam: TeamsSubF
) : Parcelable
