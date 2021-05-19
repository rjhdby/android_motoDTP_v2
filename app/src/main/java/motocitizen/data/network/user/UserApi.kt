package motocitizen.data.network.user

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("user/")
    fun getUser(@Query("token") token: String): Single<UserResponse>
}