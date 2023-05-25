package com.kevin.netkick.network.model.statistics

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.statistics.Stats

data class StatsModel(
    @SerializedName("type")
    val type: String?,
    @SerializedName("value")
    val value: Any?
) {
    companion object {
        fun transformToListEntity(item: List<StatsModel>): List<Stats> {
            return item.map {
                transformToEntity(it)
            }
        }

        fun transformToEntity(it: StatsModel): Stats {
            return Stats(
                type = it.type ?: "",
                value = when (it.value) {
                    is String -> {
                        it.value
                    }
                    is Int -> {
                        it.value.toString()
                    }
                    is Double -> {
                        it.value.toString()
                    }
                    else -> {
                        "0"
                    }
                }
            )
        }
    }
}
