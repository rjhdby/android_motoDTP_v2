package motocitizen.domain.lcenstate

/**
 * Позволяют избежать проверки на null и упростить извлечение ошибки или данных из LcenState
 */
interface LcenStateGeneric<out C : Any, out E : Any> {
    fun asContent(): C
    fun asError(): E
    fun asContentOrNull(): C?
    fun asErrorOrNull(): E?
}