package motocitizen.presentation.screens.accident.create.description

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel

class CreateDescriptionViewModel @ViewModelInject constructor(
    private val navController: NavController,
    private val accidentUseCase: AccidentUseCase,
) : BaseViewModel() {

    private val _newAccident = MutableLiveData<LcenState<Accident>>(
        LcenState.None
    )
    val newAccident: LiveData<LcenState<Accident>>
        get() = _newAccident

    lateinit var type: AccidentType
    lateinit var hardness: AccidentHardness
    lateinit var address: Address

    fun onAfterInit(type: AccidentType, hardness: AccidentHardness, address: Address) {
        this.type = type
        this.hardness = hardness
        this.address = address
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun create(description: String) {
        safeSubscribe {
            accidentUseCase.createAccident(
                type = type,
                hardness = hardness,
                location = address,
                description = description
            )
                .toLcenEventObservable { it }
                .subscribe(
                    { _newAccident.postValue(it) },
                    ::handleError
                )
        }
    }
}