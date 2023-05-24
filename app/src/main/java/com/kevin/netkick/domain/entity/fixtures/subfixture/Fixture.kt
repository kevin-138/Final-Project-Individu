package com.kevin.netkick.domain.entity.fixtures.subfixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fixture(
    val id: Int,
    val referee: String,
    val timezone: String,
    val date: String,
    val venue: VenueF,
    val status: Status
) : Parcelable
