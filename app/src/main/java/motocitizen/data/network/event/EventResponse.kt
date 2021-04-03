package motocitizen.data.network.event

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("date")
    val date: String,
)
