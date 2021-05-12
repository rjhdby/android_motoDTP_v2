package motocitizen.presentation.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.delegate

class HomeViewModel @ViewModelInject constructor(
    private val navController: NavController,
    private val getAccidentUseCase: AccidentUseCase,
    var fusedLocationProviderClient: FusedLocationProviderClient
) : BaseViewModel() {

    private val liveState = MutableLiveData(createInitialViewState())
    private var state: HomeViewState by liveState.delegate()

    private val _loadAccidentListState = MutableLiveData<LcenState<List<Accident>>>(LcenState.None)
    val loadAccidentListState: LiveData<LcenState<List<Accident>>>
        get() = _loadAccidentListState

    fun loadAccidentList() {
        safeSubscribe {
            //todo Подставить реальные данные
            getAccidentUseCase.getAccidentList(999)
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccidentListState::postValue,
                    ::handleError
                )
        }
    }

    private fun createInitialViewState(): HomeViewState {
        return HomeViewState(
            LcenState.None
        )
    }

    fun onItemPressed(item: Accident) {
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToAccidentFragment(item.id)
        )
    }
}