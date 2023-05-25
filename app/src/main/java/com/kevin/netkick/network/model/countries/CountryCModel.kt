package com.kevin.netkick.network.model.countries

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.country.CountryC

data class CountryCModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("flag")
    val flag: String?
) {
    companion object {
        fun transformToListEntity(item: List<CountryCModel?>): List<CountryC> {
            return item.map {
                transformToEntity(
                    it ?: CountryCModel(
                        name = "",
                        code = "",
                        flag = ""
                    )
                )
            }
        }

        fun transformToEntity(it: CountryCModel): CountryC {
            return CountryC(
                name = it.name ?: "",
                code = it.code ?: "",
                flag = it.flag ?: ""
            )
        }
    }
}
