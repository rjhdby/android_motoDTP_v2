package motocitizen.domain.model.accident

enum class Type(val text: String){
    BREAK("Поломка"),
    SOLO("Один участник"),
    MOTO_MOTO("Мот/мот"),
    MOTO_AUTO("Мот/авто"),
    MOTO_MAN("Наезд на пешехода"),
    OTHER("Прочее"),
    STEAL("Угон"),
    USER("user");

    fun isAccident() = this in arrayOf(MOTO_AUTO, SOLO, MOTO_MOTO, MOTO_MAN)
}