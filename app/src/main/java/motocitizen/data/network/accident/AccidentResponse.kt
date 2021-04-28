package motocitizen.data.network.accident

import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address

data class AccidentResponse(
    val id: String,
    val created: String,
    var type: AccidentType,
    var resolved: String? = null,
    var verified: Boolean,
    var hidden: Boolean,
    var hardness: AccidentHardness,
    var creator :String,
    var location: Address,
    var description: String,
    var conflict: Boolean = false,
    var messages: Int
)