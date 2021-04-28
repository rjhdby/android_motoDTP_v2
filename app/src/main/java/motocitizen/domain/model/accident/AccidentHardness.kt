package motocitizen.domain.model.accident

enum class AccidentHardness constructor(val text: String) {
    UNKNOWN("неизвестно"),
    NO("жив, цел, орёл!"),
    LIGHT("вроде цел"),
    HEAVY("вроде жив"),
    LETHAL("летальный");
}