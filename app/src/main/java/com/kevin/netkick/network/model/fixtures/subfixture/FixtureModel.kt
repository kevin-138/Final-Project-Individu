package com.kevin.netkick.network.model.fixtures.subfixture

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.subfixture.Fixture

data class FixtureModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("referee")
    val referee: String?,
    @SerializedName("timezone")
    val timezone: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("venue")
    val venue: VenueFModel?,
    @SerializedName("status")
    val status: StatusModel?
){
    companion object{
        fun transformToEntity(it: FixtureModel):Fixture{
            return Fixture(
                id = it.id ?: 0,
                referee = it.referee ?: "",
                timezone = it.timezone?: "",
                date = it.date?.substring(11,16) ?: "",
                venue = VenueFModel.transformToEntity(it.venue ?: VenueFModel("","")),
                status = StatusModel.transformToEntity(it.status ?: StatusModel("","")),
            )
        }
    }
}
