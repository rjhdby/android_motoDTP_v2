package motocitizen.data.network.version

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CheckVersionApi {

    @GET("checkVersion")
    fun checkVersion(@Query("version") version: String): Single<CheckVersionResponse>
}