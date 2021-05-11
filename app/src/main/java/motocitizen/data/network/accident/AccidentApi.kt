package motocitizen.data.network.accident

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccidentApi {
    @GET("accident/list/")
    fun getAccidentList(
        @Query("depth") depth: Int,
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("radius") radius: Int?,
        @Query("lastFetch") lastFetch: Int?,
    ): Single<List<AccidentResponse>>

    @POST("accident/create/")
    fun createAccident(@Body request: CreateAccidentRequest): Single<AccidentResponse>

    @GET("accident/")
    fun getAccident(@Query("id") id: String): Single<AccidentResponse>
}