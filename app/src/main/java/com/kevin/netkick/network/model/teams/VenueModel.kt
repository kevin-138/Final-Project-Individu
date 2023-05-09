package com.kevin.netkick.network.model.teams

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.teams.Venue

data class VenueModel(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("address")
    var address: String?,
    @SerializedName("city")
    var city: String?,
    @SerializedName("capacity")
    var capacity: Int?,
    @SerializedName("surface")
    var surface: String?,
    @SerializedName("image")
    var image: String?
){
    companion object{
        fun transformToEntity(it:VenueModel):Venue {
            return Venue(
                id = it.id ?: 0,
                name = it.name ?: "",
                address = it.address ?: "",
                city = it.city ?: "",
                capacity = it.capacity ?: 0,
                surface = it.surface ?: "",
                image = it.image ?: ""
            )
        }
    }
}
