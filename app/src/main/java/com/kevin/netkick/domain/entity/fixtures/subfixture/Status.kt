package com.kevin.netkick.domain.entity.fixtures.subfixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(
    val long: String,
    val short: String
) : Parcelable
