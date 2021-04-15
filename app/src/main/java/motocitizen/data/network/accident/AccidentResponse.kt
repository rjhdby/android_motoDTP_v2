package motocitizen.data.network.accident

import com.google.gson.annotations.SerializedName
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address

data class AccidentResponse(
        @SerializedName("id")
        val id: String,
        @SerializedName("created")
        val created: String,
        @SerializedName("type")
        var type: AccidentType,
        @SerializedName("resolved")
        var resolved: String?,
        @SerializedName("verified")
        var verified: Boolean,
        @SerializedName("hidden")
        var hidden: Boolean,
        @SerializedName("hardness")
        var hardness: AccidentHardness,
        @SerializedName("location")
        var location: Address,
        @SerializedName("description")
        var description: String,
        @SerializedName("conflict")
        var conflict: Boolean
)