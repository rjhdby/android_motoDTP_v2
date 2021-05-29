package motocitizen.data.network.message

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MessageApi {
    @GET("message/{accidentId}")
    fun getMessages(@Path("accidentId") accidentId: String): Single<List<MessageResponse>>

    @POST("message/{accidentId}")
    fun createMessage(
        @Path("accidentId") accidentId: String,
        @Query("text") text: String,
    ): Single<MessageResponse>
}