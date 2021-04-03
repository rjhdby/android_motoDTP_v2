package motocitizen.domain.model.accident

data class Accident(
        val id: String,// Идентификатор аварии
        val created: Long,// Идентификатор аварии
        var updated: Long,
//        var type: AccidentType,
        var resolved: Long? = null,
        var verified: Boolean,
        var hidden: Boolean,
//        var hardness: AccidentHardness,
//        @field:JsonSerialize(using = ToStringSerializer::class) val creator: ObjectId,
//        var location: Address,
        var description: String,
        var conflict: Boolean = false
)