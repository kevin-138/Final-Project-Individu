package com.kevin.netkick.domain.entity.fixtures.subfixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VenueF(
    val name:String,
    val city:String
) : Parcelable
