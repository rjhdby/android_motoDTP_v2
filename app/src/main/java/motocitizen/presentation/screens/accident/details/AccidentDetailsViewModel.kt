package motocitizen.presentation.screens.accident.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.requireValue

class AccidentDetailsViewModel @ViewModelInject constructor(
    private val navController: NavController,
    private val getAccidentUseCase: AccidentUseCase
) : BaseViewModel() {

    private val _loadAccident = MutableLiveData<LcenState<Accident>>(
        LcenState.None
    )
    val loadAccident: LiveData<LcenState<Accident>>
        get() = _loadAccident

    private lateinit var accidentId: String
    private lateinit var userId: String

    fun onAfterInit(accidentId: String, userId: String) {
        this.accidentId = accidentId
        this.userId = userId
    }

    fun getAccidentInfo() {
        safeSubscribe {
            getAccidentUseCase.getAccident(userId, accidentId)
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccident::postValue,
                    this::handleError
                )
        }
    }

    fun changeConflict() {
        if (loadAccident.requireValue().asContentOrNull()?.conflict!!)
            dropConflict()
        else
            setConflict()
    }

    fun resolveReopen() {
        if (loadAccident.requireValue().asContentOrNull()?.hidden!!)
            reopen()
        else
            resolve()
    }

    fun getAccident(): Accident? {
        return loadAccident.requireValue().asContentOrNull()
    }

    private fun setConflict() {
        safeSubscribe {
            getAccidentUseCase.setConflict(
                userId,
                loadAccident.requireValue().asContentOrNull()?.id!!
            )
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccident::postValue,
                    this::handleError
                )
        }
    }

    private fun dropConflict() {
        safeSubscribe {
            getAccidentUseCase.dropConflict(
                userId,
                loadAccident.requireValue().asContentOrNull()?.id!!
            )
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccident::postValue,
                    this::handleError
                )
        }
    }

    private fun reopen() {
        safeSubscribe {
            getAccidentUseCase.reopen(
                userId,
                loadAccident.requireValue().asContentOrNull()?.id!!
            )
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccident::postValue,
                    this::handleError
                )
        }
    }

    private fun resolve() {
        safeSubscribe {
            getAccidentUseCase.resolve(
                userId,
                loadAccident.requireValue().asContentOrNull()?.id!!
            )
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccident::postValue,
                    this::handleError
                )
        }
    }

    fun toMap() {
        navController.navigate(
            AccidentDetailsFragmentDirections.actionAccidentDetailsFragmentToMapFragment(getAccident()!!)
        )
    }
}