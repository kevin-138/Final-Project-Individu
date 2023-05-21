package com.kevin.netkick.network.model.league.submembers

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.league.submembers.Season

data class SeasonModel(
    @SerializedName("year")
    val year: Int?,
    @SerializedName("start")
    val start: String?,
    @SerializedName("end")
    val end: String?,
    @SerializedName("coverage")
    val coverage: CoverageModel?
){
    companion object{
        fun transformToEntityList(item: List<SeasonModel>): List<Season>{
            return item.map {
                SeasonModel.transformToEntity(
                    it
                )
            }
        }
        fun transformToEntity(it:SeasonModel): Season{
            return Season(
                year = it.year ?: 0,
                start = it.start ?: "",
                end = it.end ?: "",
                coverage =  CoverageModel.transformToEntity(it.coverage ?: CoverageModel(LeagueFixtureModel(false,false,false), false,false)))
        }
    }
}
