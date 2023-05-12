package com.kevin.netkick.network.model.countries

import PagingModel
import com.kevin.netkick.domain.entity.country.CountryResponse

data class CountryResponseModel(
    val results: Int?,
    val paging: PagingModel?,
    val response: List<CountryCModel?>?
){
    companion object{
        fun transformToEntity(it:CountryResponseModel): CountryResponse{
            return CountryResponse(
                results = it.results ?: 0,
                paging =  PagingModel.transformsToEntity(it.paging ?: PagingModel(0,0)),
                response = CountryCModel.transformToListEntity(it.response ?: listOf())
            )
        }
    }
}
