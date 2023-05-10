package com.kevin.netkick.network.model.teams

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.teams.Venue

data class VenueModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("capacity")
    val capacity: Int?,
    @SerializedName("surface")
    val surface: String?,
    @SerializedName("image")
    val image: String?
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
