package motocitizen.domain.utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

//subscribe

fun <T> Single<T>.schedulersIoToMain(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.schedulersIoToMain(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.schedulersIoToMain(): Flowable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.schedulersIoToMain(): Completable {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

//check dispose

fun <T> SingleEmitter<T>.safeOnSuccess(value: T) {
    if (!this.isDisposed) {
        this.onSuccess(value)
    }
}

fun <T> SingleEmitter<T>.safeOnError(th: Throwable) {
    if (!this.isDisposed) {
        this.onError(th)
    }
}

fun CompletableEmitter.safeOnError(th: Throwable) {
    if (!this.isDisposed) {
        this.onError(th)
    }
}