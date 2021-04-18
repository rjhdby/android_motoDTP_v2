package motocitizen.domain.model.accident

enum class Hardness constructor(val text: String) {
    UNKNOWN("неизвестно"),
    NO("жив, цел, орёл!"),
    LIGHT("вроде цел"),
    HEAVY("вроде жив"),
    LETHAL("летальный");
}
