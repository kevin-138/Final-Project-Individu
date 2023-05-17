package com.kevin.netkick.network.model.standings.substandings

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.standings.substandings.Standings

data class StandingsModel(
    @SerializedName("rank")
    val rank: Int?,
    @SerializedName("team")
    val team: TeamStModel?,
    @SerializedName("points")
    val points: Int?,
    @SerializedName("goalsDiff")
    val goalsDiff: Int?,
    @SerializedName("group")
    val group: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("all")
    val all: AllModel?
){
    companion object{

        fun transformToListEntity(item:List<StandingsModel>):List<Standings>{
            return item.map {
                transformToEntity(it)
            }
        }
        fun transformToEntity(it:StandingsModel):Standings{
            return Standings(
                rank = it.rank ?: 0,
                team = TeamStModel.transformToEntity(it.team ?: TeamStModel(0,"","")),
                points = it.points ?: 0,
                goalsDiff = it.goalsDiff ?: 0,
                group = it.group ?: "",
                description = it.description ?: "",
                all = AllModel.transformToEntity(it.all ?: AllModel(0, GoalsStModel(0,0),0,0,0))
            )
        }
    }
}
