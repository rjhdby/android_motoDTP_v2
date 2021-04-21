package motocitizen.domain.model.accident

enum class AccidentType {
    OTHER, BREAK, STEAL, SOLO, MOTO_AUTO, MOTO_MOTO, MOTO_PEDESTRIAN, SPAM;

    fun isAccident() = this in arrayOf(
        MOTO_AUTO,
        SOLO,
        MOTO_MOTO,
        MOTO_PEDESTRIAN
    )
}