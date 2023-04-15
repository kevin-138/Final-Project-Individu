package com.kevin.netkick.network.model.fixtures

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.domain.entity.fixtures.ResponseF
import com.kevin.netkick.domain.entity.general.PagingModel
import com.kevin.netkick.network.model.fixtures.subfixture.FixtureModel
import com.kevin.netkick.network.model.fixtures.subfixture.StatusModel
import com.kevin.netkick.network.model.fixtures.subfixture.VenueFModel
import com.kevin.netkick.network.model.fixtures.subscore.ScoreModel
import com.kevin.netkick.network.model.fixtures.subscore.SubScoreModel
import com.kevin.netkick.network.model.fixtures.subteams.TeamsFModel
import com.kevin.netkick.network.model.fixtures.subteams.TeamsSubFModel

data class FixturesResponseModel(
    @SerializedName("paging")
    val paging: PagingModel?,
    @SerializedName("results")
    val results: Int?,
    @SerializedName("response")
    val response: List<ResponseFModel?>?
){
    companion object{
        fun transformToEntity(it:FixturesResponseModel):FixturesResponse{
            return FixturesResponse(
                paging = PagingModel.transformsToEntity(it.paging ?: PagingModel(0,0)),
                results = it.results ?: 0,
                response = ResponseFModel.transformToListEntity(it.response ?: listOf())
            )
        }
    }
}
