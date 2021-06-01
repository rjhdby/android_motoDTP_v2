package motocitizen.presentation.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import motocitizen.data.repos.SettingsDataRepo
import motocitizen.data.network.user.User
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.domain.usecases.GetUserUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel

class HomeViewModel @ViewModelInject constructor(
    private val navController: NavController,
    private val getUser: GetUserUseCase,
    private val getAccidentUseCase: AccidentUseCase,
    private val settingsDataRepo: SettingsDataRepo
) : BaseViewModel() {

    lateinit var user: User

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
        val depth = settingsDataRepo.getDepth().toInt()
        val radius = settingsDataRepo.getDistance().toInt()
        safeSubscribe {
            getAccidentUseCase.getAccidentList(
                lat = lat,
                lon = lon,
                radius = radius,
                depth = depth
            )
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccidentListState::postValue,
                    ::handleError
                )
        }
    }

    fun onItemPressed(item: Accident) {
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToAccidentDetailsFragment(
                item.id,
                userState.value!!.asContent().id,
                true
            )
        )
    }
}