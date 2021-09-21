package motocitizen.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.data.network.user.User
import motocitizen.data.repos.SettingsDataRepo
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.domain.usecases.GetUserUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val getAccidentUseCase: AccidentUseCase,
    private val settingsDataRepo: SettingsDataRepo
) : BaseViewModel() {

    private val _loadAccidentListState = MutableLiveData<LcenState<List<Accident>>>(LcenState.None)
    val loadAccidentListState: LiveData<LcenState<List<Accident>>>
        get() = _loadAccidentListState

    private val _userState = MutableLiveData<LcenState<User>>(LcenState.None)
    val userState: LiveData<LcenState<User>>
        get() = _userState

    fun loadUser() {
        safeSubscribe {
            getUser(skipCache = false)
                .toLcenEventObservable { it }
                .subscribe(
                    _userState::postValue,
                    ::handleError
                )
        }
    }

    fun loadAccidentList(lat: Double, lon: Double) {
        safeSubscribe {
            getAccidentUseCase.getAccidentList(
                lat = lat,
                lon = lon,
                radius = settingsDataRepo.getDistance().toInt(),
                depth = settingsDataRepo.getDepth().toInt(),
                userId = userState.value!!.asContent().id
            )
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccidentListState::postValue,
                    ::handleError
                )
        }
    }
}