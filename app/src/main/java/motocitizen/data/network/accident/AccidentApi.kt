package motocitizen.data.network.accident

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AccidentApi {
    @GET("/accident/list/")
    fun getAccidentList(
        @Query("token") token: String,
        @Query("depth") depth: Int,
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("radius") radius: Int?,
        @Query("lastFetch") lastFetch: Int?,
    ): Single<List<AccidentResponse>>
}