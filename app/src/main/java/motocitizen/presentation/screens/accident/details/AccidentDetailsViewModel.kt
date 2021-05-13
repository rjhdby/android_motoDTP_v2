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

    fun getAccidentInfo(accidentId: String) {
        safeSubscribe {
            getAccidentUseCase.getAccident(accidentId)
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

    fun getAccident(): Accident? {
        return loadAccident.requireValue().asContentOrNull()
    }

    private fun setConflict() {
        safeSubscribe {
            getAccidentUseCase.setConflict(loadAccident.requireValue().asContentOrNull()?.id!!)
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccident::postValue,
                    this::handleError
                )
        }
    }

    private fun dropConflict() {
        safeSubscribe {
            getAccidentUseCase.dropConflict(loadAccident.requireValue().asContentOrNull()?.id!!)
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