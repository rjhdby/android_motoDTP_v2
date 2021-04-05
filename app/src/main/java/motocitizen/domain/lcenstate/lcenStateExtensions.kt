package motocitizen.domain.lcenstate

import io.reactivex.Observable

fun <T : Any> LcenState<T>.isNone(): Boolean {
    return this is LcenState.None
}

fun <T : Any> LcenState<T>.isContent(): Boolean {
    return this is LcenState.Content
}

fun <T : Any> LcenState<T>.isLoading(): Boolean {
    return this == LcenState.Loading
}

fun <T : Any> LcenState<T>.isError(): Boolean {
    return this is LcenState.Error
}

fun <T : Any> Observable<LcenState<T>>.doOnNextContent(
    action: (T) -> Unit
): Observable<LcenState<T>> {
    return doOnNext {
        if (it is LcenState.Content) {
            action.invoke(it.value)
        }
    }
}