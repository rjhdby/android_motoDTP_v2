package motocitizen.data.network.nomination

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface NominationApi {

    @GET("nomination/")
    fun getNomination(@Query("lat") lat: Double, @Query("lon") lon: Double): Single<ResponseBody>
}