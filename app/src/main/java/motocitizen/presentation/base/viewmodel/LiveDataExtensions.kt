package motocitizen.presentation.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> MutableLiveData<T>.onNext(data: T) {
    this.value = data
}

fun <T : Any> LiveData<T>.requireValue(): T = checkNotNull(value)

/**
 * Делегат для работы с содержимым [MutableLiveData] как с полем.
 *
 * Без делегата пришлось бы писать такой код:
 * ```
 *  val liveState = MutableLiveData<IntroViewState>(initialState)
 *  var state: IntroViewState
 *      get() = liveState.requireValue()
 *      set(value) = liveState.onNext(value)
 * ```
 * С делегатом для такой же логики достаточно написать:
 * ```
 *  val liveState = MutableLiveData<IntroViewState>(initialState)
 *  var state: IntroViewState by liveState.delegate()
 * ```
 */
fun <T : Any> MutableLiveData<T>.delegate(): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {
        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = onNext(value)
        override fun getValue(thisRef: Any, property: KProperty<*>): T = requireValue()
    }
}

/** Последовательный вызов [map] и [distinctUntilChanged], в одной функции. */
inline fun <X, Y> LiveData<X>.mapDistinct(crossinline transform: (X) -> Y): LiveData<Y> {
    return map(transform).distinctUntilChanged()
}