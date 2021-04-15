package motocitizen.presentation.screens.accident.create.description

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.*
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

    fun onAfterInit(type: AccidentType, hardness: AccidentHardness) {
        this.type = type
        this.hardness = hardness
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun create(description: String) {
        safeSubscribe {
            accidentUseCase.createAccident(
                type = type,
                hardness = hardness,
                //todo Убрать после реализации
                location = Address(54.0f, 37.1f, "Москва"),
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