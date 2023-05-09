
import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.teams.Team

data class TeamModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("founded")
    val founded: Int?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("national")
    val national: Boolean?
){
    companion object{
        fun transformToEntity(it: TeamModel): Team {
            return Team(
                id = it.id ?: 0,
                name = it.name ?: "",
                code = it.code ?: "",
                country = it.country  ?: "",
                founded = it.founded ?: 0,
                logo = it.logo ?: "",
                national = it.national ?: false
            )
        }
    }
}
