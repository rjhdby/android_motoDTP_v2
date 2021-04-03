package motocitizen.data.network.version

import com.google.gson.annotations.SerializedName

data class CheckVersionResponse(
    @SerializedName("status")
    val status: VersionStatus
)

enum class VersionStatus(val value: String) {
    @SerializedName("normal")
    NORMAL("normal"),

    @SerializedName("deprecated")
    DEPRECATED("deprecated "),

    @SerializedName("unsupported")
    UNSUPPORTED("unsupported ")
}