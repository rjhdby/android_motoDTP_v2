package motocitizen.domain.model.accident

enum class AccidentType (val text: String) {
    OTHER("Прочее"),
    BREAK("Поломка"),
    STEAL("Угон"),
    SOLO("Один участник"),
    MOTO_MOTO("Мот/мот"),
    MOTO_AUTO("Мот/авто"),
    MOTO_PEDESTRIAN("Наезд на пешехода"),
    SPAM("Спам");

    fun isAccident() = this in arrayOf(
        MOTO_AUTO,
        SOLO,
        MOTO_MOTO,
        MOTO_PEDESTRIAN
    )
}