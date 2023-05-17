package com.kevin.netkick.network.model.standings

import com.kevin.netkick.domain.entity.standings.Group
import com.kevin.netkick.network.model.standings.substandings.StandingsModel

data class GroupModel(
    val group: List<StandingsModel>?
){
    companion object{
        fun transformToListEntity(item:List<GroupModel>):List<Group>{
            return item.map {
                transformToEntity(it)
            }
        }
        fun transformToEntity(it:GroupModel):Group{
            return Group(StandingsModel.transformToListEntity(it.group ?: listOf()))
        }
    }
}
