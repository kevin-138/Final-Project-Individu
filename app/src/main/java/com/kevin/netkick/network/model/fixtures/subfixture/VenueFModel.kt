package com.kevin.netkick.network.model.fixtures.subfixture

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.subfixture.VenueF

data class VenueFModel(
    @SerializedName("name")
    val name:String?,
    @SerializedName("city")
    val city:String?
){
    companion object{
        fun transformToEntity(it: VenueFModel):VenueF{
            return VenueF(
                name = it.name ?: "",
                city = it.city ?: ""
            )
        }
    }
}

