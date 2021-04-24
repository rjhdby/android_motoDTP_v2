package motocitizen.data.network.accident

import com.google.gson.annotations.SerializedName
import motocitizen.domain.model.accident.Type
import motocitizen.domain.model.accident.Address
import motocitizen.domain.model.accident.Hardness

data class AccidentResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("created")
    val created: String,
    @SerializedName("type")
    var type: Type,
    @SerializedName("resolved")
    var resolved: String? = null,
    @SerializedName("verified")
    var verified: Boolean,
    @SerializedName("hidden")
    var hidden: Boolean,
    @SerializedName("hardness")
    var hardness: Hardness,
    @SerializedName("creator")
    var creator :String,
//    @SerializedName("updated")
//    var updated: String,
//        var type: AccidentType,
//        var hardness: AccidentHardness,
//        @field:JsonSerialize(using = ToStringSerializer::class) val creator: ObjectId,
    @SerializedName("location")
    var location: Address,
    @SerializedName("description")
    var description: String,
    @SerializedName("conflict")
    var conflict: Boolean = false,
    @SerializedName("messages")
    var messages: Int
)