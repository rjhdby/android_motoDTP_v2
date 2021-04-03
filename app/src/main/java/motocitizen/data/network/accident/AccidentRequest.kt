package motocitizen.data.network.accident

import com.google.gson.annotations.SerializedName

data class AccidentRequest(
        @SerializedName("token")
        val token: String,
        @SerializedName("depth")
        val depth: Int,
        @SerializedName("lat")
        val lat: Double?,
        @SerializedName("lon")
        val lon: Double?,
        @SerializedName("radius")
        val radius: Int?,
        @SerializedName("lastFetch")
        val lastFetch: Int?,
)