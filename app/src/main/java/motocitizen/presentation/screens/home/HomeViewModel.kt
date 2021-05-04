package motocitizen.presentation.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import motocitizen.di.module.application.ApplicationModule
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.domain.usecases.CheckVersionUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.delegate
import motocitizen.presentation.base.viewmodel.mapDistinct

class HomeViewModel @ViewModelInject constructor(
    private val checkVersion: CheckVersionUseCase,
    private val getAccidentUseCase: AccidentUseCase,
    var fusedLocationProviderClient: FusedLocationProviderClient
) : BaseViewModel() {

    private val liveState = MutableLiveData(createInitialViewState())
    private var state: HomeViewState by liveState.delegate()

    val homeViewState: LiveData<HomeViewState> = liveState.mapDistinct { it }

    private val _loadAccidentListState = MutableLiveData<LcenState<List<Accident>>>(LcenState.None)
    val loadAccidentListState: LiveData<LcenState<List<Accident>>>
        get() = _loadAccidentListState

    fun loadAccidentList() {
        safeSubscribe {
            //todo Подставить реальные данные
            getAccidentUseCase.getAccidentList("1", 999)
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccidentListState::postValue,
                    ::handleError
                )
        }
    }

    fun onAfterInit() {
        getVersionStatus()
    }

    private fun getVersionStatus() {
        safeSubscribe {
            checkVersion()
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(checkVersionState = it) },
                    ::handleError
                )
        }
    }

    private fun createInitialViewState(): HomeViewState {
        return HomeViewState(
            checkVersionState = LcenState.None,
            checkRestrictionsState = LcenState.None,
            plannedWorkCurrentPage = 0,
        )
    }
}