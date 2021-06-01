package motocitizen.presentation.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import motocitizen.data.repos.SettingsDataRepo
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel

class HomeViewModel @ViewModelInject constructor(
    private val navController: NavController,
    private val getAccidentUseCase: AccidentUseCase,
    private val settingsDataRepo: SettingsDataRepo
) : BaseViewModel() {

    private val liveState = MutableLiveData(createInitialViewState())
    private val _loadAccidentListState = MutableLiveData<LcenState<List<Accident>>>(LcenState.None)
    val loadAccidentListState: LiveData<LcenState<List<Accident>>>
        get() = _loadAccidentListState

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

    private fun createInitialViewState(): HomeViewState {
        return HomeViewState(
            LcenState.None
        )
    }

    fun onItemPressed(item: Accident) {
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToAccidentDetailsFragment(item.id, true)
        )
    }
}