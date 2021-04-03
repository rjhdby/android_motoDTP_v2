package motocitizen.data.network.event

import com.google.gson.annotations.SerializedName

data class EventRequest(
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("finishDate")
    val finishDate: String,
    @SerializedName("timezone")
    val timezone: String
)