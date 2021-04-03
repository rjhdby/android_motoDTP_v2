package motocitizen.data.network.restrictions

import io.reactivex.Single
import retrofit2.http.GET

interface RestrictionsApi {
    @GET("restrictions")
    fun restrictions(): Single<RestrictionsResponse>
}