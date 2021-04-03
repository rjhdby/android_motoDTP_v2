package motocitizen.data.network.login

import com.google.gson.annotations.SerializedName

data class SignInApiRequest(
    @SerializedName("installId")
    val installId: String, // Идентификатор устройства *
    @SerializedName("token")
    val token: String, // Токен пуша *
    @SerializedName("platform")
    val platform: String // Платформа ios/android *
)