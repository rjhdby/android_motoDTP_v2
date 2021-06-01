package motocitizen.data.network.accident

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

interface AccidentApi {
    @GET("accident/list/")
    fun getAccidentList(
        @Query("depth") depth: Int,
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("radius") radius: Int?,
        @Query("lastFetch") lastFetch: Int?,
    ): Single<List<AccidentResponse>>

    @POST("accident/")
    fun createAccident(@Body request: CreateAccidentRequest): Single<ResponseBody>

    @GET("accident/{id}")
    fun getAccident(@Path("id") id: String): Single<AccidentResponse>

    @PUT("accident/{id}/conflict")
    fun setConflict(@Path("id") id: String): Single<AccidentResponse>

    @PUT("accident/{id}/conflict/cancel")
    fun dropConflict(@Path("id") id: String): Single<AccidentResponse>
}