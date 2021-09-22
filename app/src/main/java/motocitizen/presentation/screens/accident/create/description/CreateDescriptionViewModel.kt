package motocitizen.presentation.screens.accident.create.description

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.delegate
import motocitizen.presentation.base.viewmodel.mapDistinct
import javax.inject.Inject

@HiltViewModel
class CreateDescriptionViewModel @Inject constructor(
    private val accidentUseCase: AccidentUseCase,
) : BaseViewModel() {

    private val liveState = MutableLiveData(createInitialViewState())
    private var state: CreateDescriptionViewState by liveState.delegate()

    val createState = liveState.mapDistinct { it.newAccident }

    lateinit var type: AccidentType
    lateinit var address: Address

    var hardness: AccidentHardness? = null

    private fun createInitialViewState(): CreateDescriptionViewState {
        return CreateDescriptionViewState(newAccident = LcenState.None)
    }

    fun onAfterInit(type: AccidentType, hardness: AccidentHardness?, address: Address) {
        this.type = type
        this.hardness = hardness
        this.address = address
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
                    { state = state.copy(newAccident = it) },
                    ::handleError
                )
        }
    }
}