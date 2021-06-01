package motocitizen.presentation.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
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
        safeSubscribe {
            getAccidentUseCase.getAccidentList(
                999,
                lat = lat,
                lon = lon,
                userId = userState.value!!.asContent().id,
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