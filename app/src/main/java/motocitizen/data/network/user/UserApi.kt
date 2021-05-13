package motocitizen.data.network.user

import io.reactivex.Single
import retrofit2.http.GET

interface UserApi {
    @GET("user/")
    fun getUser(): Single<UserResponse>
}