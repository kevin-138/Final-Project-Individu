package com.kevin.netkick.network.model.standings

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.standings.Group
import com.kevin.netkick.domain.entity.standings.LeagueSt

data class LeagueStModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("season")
    val season: Int?,
    @SerializedName("standings")
    val standings: List<GroupModel>?
){
    companion object{
        fun transformToEntity(it:LeagueStModel):LeagueSt{
            return LeagueSt(
                id = it.id ?: 0,
                name = it.name ?: "",
                country = it.country ?: "",
                logo = it.logo ?: "",
                season = it.season ?: 0,
                standings = GroupModel.transformToListEntity(it.standings ?: listOf())
            )
        }
    }
}
