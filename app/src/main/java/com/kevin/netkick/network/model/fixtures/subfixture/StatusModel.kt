package com.kevin.netkick.network.model.fixtures.subfixture

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.subfixture.Status

data class StatusModel(
    @SerializedName("long")
    val long: String?,
    @SerializedName("short")
    val short: String?
) {
    companion object {
        fun transformToEntity(it: StatusModel): Status {
            return Status(
                long = it.long ?: "",
                short = it.short ?: ""
            )
        }
    }
}
