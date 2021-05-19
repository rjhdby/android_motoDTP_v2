package motocitizen.domain.lcenstate

/**
 * Описывает возможные состояния результата какого-либо действия:
 * неинициализированное состояние (None),
 * состояние загрузки (Loading),
 * состояние успешного обновления данных (Content),
 * состояние ошибки (Error)
 */
sealed class LcenState<out T : Any> :
    LcenStateGeneric<T, Throwable> {
    object None : LcenState<Nothing>()
    object Loading : LcenState<Nothing>()
    data class Content<C : Any>(val value: C) : LcenState<C>()
    data class Error(val value: Throwable) : LcenState<Nothing>()

    override fun asContent(): T {
        return (this as Content<T>).value
    }

    override fun asError(): Throwable {
        return (this as Error).value
    }

    override fun asContentOrNull(): T? {
        return (this as? Content<T>)?.value
    }

    override fun asErrorOrNull(): Throwable? {
        return (this as? Error)?.value
    }

    override fun toString(): String = javaClass.simpleName

    // Дефолтная реализация equals и hashCode для None и Loading.
    // Без этого Epoxy не сможет использовать LcenState с аннотацией @ModelProp
    override fun equals(other: Any?): Boolean = this === other
    override fun hashCode(): Int = javaClass.hashCode()
}