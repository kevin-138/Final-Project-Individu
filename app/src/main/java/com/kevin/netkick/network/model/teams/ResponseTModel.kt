import com.kevin.netkick.domain.entity.teams.ResponseT
import com.kevin.netkick.network.model.teams.VenueModel

data class ResponseTModel(
    val team: TeamModel?,
    val venue: VenueModel?
) {
    companion object {

        fun transformToListEntity(item: List<ResponseTModel?>): List<ResponseT> {
            return item.map {
                transformToEntity(
                    it ?: ResponseTModel(
                        team = TeamModel(
                            0, "", "", "", 0, "", false
                        ),
                        venue = VenueModel(
                            0, "", "", "", 0, "", ""
                        ),
                    )
                )
            }
        }

        fun transformToEntity(it: ResponseTModel): ResponseT {
            return ResponseT(
                team = TeamModel.transformToEntity(
                    it.team ?: TeamModel(
                        0, "", "", "", 0, "", false
                    )
                ),
                venue = VenueModel.transformToEntity(
                    it.venue ?: VenueModel(
                        0, "", "", "", 0, "", ""
                    )
                )
            )
        }
    }
}
