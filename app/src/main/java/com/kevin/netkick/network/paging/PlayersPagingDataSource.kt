package com.kevin.netkick.network.paging

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kevin.netkick.domain.entity.player.ResponseP
import com.kevin.netkick.network.model.player.ResponsePModel
import com.kevin.netkick.network.service.FootballApiService
import kotlinx.coroutines.flow.Flow

class PlayersPagingDataSource(private val apiService: FootballApiService, private val team: Int, private val season: Int):PagingSource<Int, ResponseP>() {

    override fun getRefreshKey(state: PagingState<Int, ResponseP>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseP> {
        val position = params.key ?: 1
        return try {
            val listData = apiService.getPlayerInTeams(team,season,)
            LoadResult.Page(
                data = ResponsePModel.transformToListEntity(listData.response),
                nextKey = if ((listData.paging?.current ?: 0) == (listData.paging?.total ?: 0)) null else position + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

//    private suspend fun getPlayerInTeams(team:Int,season: Int): List<ResponseP> {
//        val response = apiService.getPlayerInTeams()
//        val data = Respon.transforms(response.results ?: listOf<ReviewModel>())
//        return data
//    }
}