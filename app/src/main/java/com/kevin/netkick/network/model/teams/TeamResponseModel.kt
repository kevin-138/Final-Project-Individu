import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.teams.TeamResponse

data class TeamResponseModel(
    @SerializedName("paging")
    val paging: PagingModel?,
    @SerializedName("results")
    val results: Int?,
    @SerializedName("response")
    val response: List<ResponseTModel?>?
) {
    companion object {
        fun transfromToEntity(it: TeamResponseModel): TeamResponse {
            return TeamResponse(
                paging = PagingModel.transformsToEntity(it.paging ?: PagingModel(0, 0)),
                results = it.results ?: 0,
                response = ResponseTModel.transformToListEntity(
                    it.response ?: listOf()
                )
            )
        }
    }
}
