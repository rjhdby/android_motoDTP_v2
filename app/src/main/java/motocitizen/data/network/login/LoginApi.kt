package motocitizen.data.network.login

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("pushToken/update")
    fun update(@Body data: SignInApiRequest): Single<Response<SignInApiResponse>?>
}