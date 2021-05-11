package motocitizen.presentation.screens.accident.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel

class AccidentDetailsViewModel @ViewModelInject constructor(
    private val getAccidentUseCase: AccidentUseCase
) : BaseViewModel() {
    private val _loadAccident = MutableLiveData<LcenState<Accident>>(
        LcenState.None)
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
}