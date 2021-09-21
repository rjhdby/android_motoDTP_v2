package motocitizen.presentation.screens.accident.create.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class CreateDescriptionViewModel @Inject constructor(
    private val accidentUseCase: AccidentUseCase,
) : BaseViewModel() {

    private val _newAccident = MutableLiveData<LcenState<ResponseBody>>(
        LcenState.None
    )
    val newAccident: LiveData<LcenState<ResponseBody>>
        get() = _newAccident

    lateinit var type: AccidentType
    lateinit var address: Address

    var hardness: AccidentHardness? = null

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
                    { _newAccident.postValue(it) },
                    ::handleError
                )
        }
    }
}