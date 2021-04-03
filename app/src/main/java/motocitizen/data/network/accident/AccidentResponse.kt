package motocitizen.data.network.accident

import com.google.gson.annotations.SerializedName

data class AccidentResponse(
        @SerializedName("id")
        val id: String,
        @SerializedName("created")
        val created: Long,
        @SerializedName("updated")
        var updated: Long,
//        var type: AccidentType,
        @SerializedName("resolved")
        var resolved: Long? = null,
        @SerializedName("verified")
        var verified: Boolean,
        @SerializedName("hidden")
        var hidden: Boolean,
//        var hardness: AccidentHardness,
//        @field:JsonSerialize(using = ToStringSerializer::class) val creator: ObjectId,
//        var location: Address,
        @SerializedName("description")
        var description: String,
        @SerializedName("conflict")
        var conflict: Boolean = false
)