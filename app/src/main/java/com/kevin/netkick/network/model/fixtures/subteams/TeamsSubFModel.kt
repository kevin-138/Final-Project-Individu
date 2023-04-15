package com.kevin.netkick.network.model.fixtures.subteams

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.subteams.TeamsSubF

data class TeamsSubFModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("winner")
    val winner: Boolean?
){
    companion object{
        fun transformToEntity(it:TeamsSubFModel):TeamsSubF{
            return TeamsSubF(
                id = it.id ?: 0,
                name = it.name ?: "",
                logo = it.logo ?: "",
                winner = it.winner ?: false
            )
        }
    }
}
