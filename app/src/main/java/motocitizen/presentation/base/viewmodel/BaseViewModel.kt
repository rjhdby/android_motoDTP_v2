package motocitizen.presentation.base.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import motocitizen.domain.exceptions.AuthException
import motocitizen.domain.exceptions.LockedException
import motocitizen.presentation.base.viewmodel.commands.CommandsLiveData
import motocitizen.presentation.base.viewmodel.commands.VMCommand
import motocitizen.presentation.screens.root.ForceChooseCertificate
import timber.log.Timber
import javax.net.ssl.SSLException

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    val commands =
        CommandsLiveData<VMCommand>()
    val showError =
        CommandsLiveData<Throwable>()

    override fun onCleared() {
        super.onCleared()
        clearAllObservables()
    }

    protected fun safeSubscribe(block: () -> Disposable) {
        compositeDisposable.add(block.invoke())
    }

    protected fun handleError(th: Throwable) = handleError(th) {
        Timber.e(it)
        showError.onNext(th)
    }

    protected fun handleError(th: Throwable, handler: ((Throwable) -> (Unit))) {
        when (th) {
            is SSLException -> {
                commands.onNext(ForceChooseCertificate)
                handler.invoke(th)
            }
            is AuthException -> {
                //commands.onNext(Logout)
                handler.invoke(th)
            }
            is LockedException -> {
                //commands.onNext(CheckIn)
                handler.invoke(th)
            }
            else -> handler.invoke(th)
        }
    }

    private fun clearAllObservables() {
        compositeDisposable.clear()
    }
}