package motocitizen.domain.model.accident

data class Accident(
    val id: String,// Идентификатор аварии
    val created: String,// Идентификатор аварии
    var type: AccidentType,
    var resolved: String?,
    var verified: Boolean,
    var hidden: Boolean,
    var hardness: AccidentHardness,
    var location: Address,
    var description: String,
    var conflict: Boolean
)