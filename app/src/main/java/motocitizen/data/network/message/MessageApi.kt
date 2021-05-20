package motocitizen.data.network.message

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MessageApi {
    @GET("message/{accidentId}")
    fun getMessages(@Path("accidentId") accidentId: String): Single<List<MessageResponse>>
}