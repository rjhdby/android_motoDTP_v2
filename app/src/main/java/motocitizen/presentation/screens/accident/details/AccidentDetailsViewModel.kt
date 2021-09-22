package motocitizen.presentation.screens.accident.details

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.commands.VMCommand
import motocitizen.presentation.base.viewmodel.delegate
import motocitizen.presentation.base.viewmodel.mapDistinct
import javax.inject.Inject

@HiltViewModel
class AccidentDetailsViewModel @Inject constructor(
    private val getAccidentUseCase: AccidentUseCase
) : BaseViewModel() {

    private lateinit var accidentId: String
    private lateinit var userId: String

    private val liveState = MutableLiveData(createInitialViewState())
    private var state: AccidentDetailsViewState by liveState.delegate()
    val loadAccidentState = liveState.mapDistinct { it.loadAccident }

    private fun createInitialViewState(): AccidentDetailsViewState {
        return AccidentDetailsViewState(
            loadAccident = LcenState.None
        )
    }

    fun onAfterInit(accidentId: String, userId: String) {
        this.accidentId = accidentId
        this.userId = userId
    }

    fun getAccidentInfo() {
        safeSubscribe {
            getAccidentUseCase.getAccident(userId, accidentId)
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(loadAccident = it) },
                    this::handleError
                )
        }
    }

    fun changeConflict() {
        if (state.loadAccident.asContentOrNull()?.conflict!!)
            dropConflict()
        else
            setConflict()
    }

    fun resolveReopen() {
        if (state.loadAccident.asContentOrNull()?.hidden!!)
            reopen()
        else
            resolve()
    }

    fun getAccident(): Accident? {
        return state.loadAccident.asContentOrNull()
    }

    private fun setConflict() {
        safeSubscribe {
            getAccidentUseCase.setConflict(
                userId,
                state.loadAccident.asContentOrNull()?.id!!
            )
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(loadAccident = it) },
                    this::handleError
                )
        }
    }

    private fun dropConflict() {
        safeSubscribe {
            getAccidentUseCase.dropConflict(
                userId,
                state.loadAccident.asContentOrNull()?.id!!
            )
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(loadAccident = it) },
                    this::handleError
                )
        }
    }

    private fun reopen() {
        safeSubscribe {
            getAccidentUseCase.reopen(
                userId,
                state.loadAccident.asContentOrNull()?.id!!
            )
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(loadAccident = it) },
                    this::handleError
                )
        }
    }

    private fun resolve() {
        safeSubscribe {
            getAccidentUseCase.resolve(
                userId,
                state.loadAccident.asContentOrNull()?.id!!
            )
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(loadAccident = it) },
                    this::handleError
                )
        }
    }

    fun toMap() {
        commands.onNext(ToMap(getAccident()!!))
    }
}

data class ToMap(val accident: Accident?) : VMCommand