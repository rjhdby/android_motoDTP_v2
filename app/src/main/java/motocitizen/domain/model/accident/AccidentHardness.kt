package motocitizen.domain.model.accident

enum class AccidentHardness constructor(val text: String) {
    UNKNOWN("неизвестно"),
    NO("жив, цел, орёл!"),
    LIGHT("вроде цел"),
    HEAVY("вроде жив"),
    LETHAL("летальный"),
    NULL("отсутствует"); //todo #69 Переделать когда Custom Enum supports null values
}