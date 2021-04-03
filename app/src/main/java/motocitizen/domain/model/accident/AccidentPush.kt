package motocitizen.domain.model.accident

data class AccidentPush (
    val pushId: Int,
    val accidentId: String,
    val accidentName: String,
    val description: String,
    val header: String
)