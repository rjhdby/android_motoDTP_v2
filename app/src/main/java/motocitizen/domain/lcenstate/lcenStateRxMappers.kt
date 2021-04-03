package motocitizen.domain.lcenstate

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Преобразуют результат Completable в Observable типа, получаемого в результате
 * преобразования LcenState
 */

fun <T> Completable.toLcenEventObservable(stateCreator: (LcenState<Unit>) -> T): Observable<T> {
    return this.andThen(Observable.fromCallable { stateCreator(
        LcenState.Content(
            Unit
        )
    ) })
        .onErrorReturn { stateCreator(
            LcenState.Error(
                it
            )
        ) }
        .startWith(stateCreator(LcenState.Loading))
}

/**
 * Преобразуют результат Single в Observable типа, получаемого в результате
 * преобразования LcenState
 */
fun <C : Any, T> Single<C>.toLcenEventObservable(stateCreator: (LcenState<C>) -> T): Observable<T> {
    return map { stateCreator(
        LcenState.Content(
            it
        )
    ) }
        .onErrorReturn { stateCreator(
            LcenState.Error(
                it
            )
        ) }
        .toObservable()
        .startWith(stateCreator(LcenState.Loading))
}

