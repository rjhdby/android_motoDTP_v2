package motocitizen.data.network.login

import com.google.gson.annotations.SerializedName

data class SignInApiResponse(
    @SerializedName("token")
    val token: String?
)