package motocitizen.presentation.screens.home

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.data.repos.SettingsDataRepo
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.domain.usecases.GetUserUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.delegate
import motocitizen.presentation.base.viewmodel.mapDistinct
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val getAccidentUseCase: AccidentUseCase,
    private val settingsDataRepo: SettingsDataRepo
) : BaseViewModel() {

    private val liveState = MutableLiveData(createInitialViewState())

    private var state: HomeViewState by liveState.delegate()

    val loadUserState = liveState.mapDistinct { it.user }
    val loadAccidentListState = liveState.mapDistinct { it.accidentList }

    fun loadUser() {
        safeSubscribe {
            getUser(skipCache = false)
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(user = it) },
                    ::handleError
                )
        }
    }

    private fun createInitialViewState(): HomeViewState {
        return HomeViewState(
            accidentList = LcenState.None,
            user = LcenState.None,
        )
    }

    fun getAccidentList(lat: Double, lon: Double) {
        safeSubscribe {
            getAccidentUseCase.getAccidentList(
                lat = lat,
                lon = lon,
                radius = settingsDataRepo.getDistance().toInt(),
                depth = settingsDataRepo.getDepth().toInt(),
                userId = state.user.asContent().id
            )
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(accidentList = it) },
                    ::handleError
                )
        }
    }
}