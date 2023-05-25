import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.general.Paging

data class PagingModel(
    @SerializedName("current")
    val current: Int,
    @SerializedName("total")
    val total: Int
) {
    companion object {
        fun transformsToEntity(it: PagingModel): Paging {
            return Paging(
                current = it.current,
                total = it.total
            )
        }
    }
}
