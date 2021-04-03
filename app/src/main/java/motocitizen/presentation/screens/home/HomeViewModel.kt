package motocitizen.presentation.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.isContent
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.usecases.CheckVersionUseCase
import motocitizen.domain.usecases.GetRestrictionsUseCase
import motocitizen.domain.usecases.LoginUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.delegate
import motocitizen.presentation.base.viewmodel.mapDistinct
import org.joda.time.DateTime

private const val NOW_CHANGES_PAGE = 0
private const val FUTURE_CHANGES_PAGE = 1

class HomeViewModel @ViewModelInject constructor(
        private val checkVersion: CheckVersionUseCase,
        private val getRestrictions: GetRestrictionsUseCase,
        private val loginUseCase: LoginUseCase,
) : BaseViewModel() {

    private val liveState = MutableLiveData(createInitialViewState())
    private var state: HomeViewState by liveState.delegate()

    val homeViewState: LiveData<HomeViewState> = liveState.mapDistinct { it }

    private fun loadData() {
        loadNewAccident()
        loadMetrics()
        loadSystems()
        loadPlannedWork(state.plannedWorkCurrentPage)
    }

    fun loadRestrictions() {
        safeSubscribe {
            getRestrictions()
                .toLcenEventObservable { it }
                .subscribe(
                    {
                        state = state.copy(checkRestrictionsState = it)
                        if (it.isContent()) {
                            loadData()
                        }
                    },
                    ::handleError
                )
        }
    }

    fun loadNewAccident() {
    }

    fun loadMetrics() {
        if (state.checkRestrictionsState.asContentOrNull()?.metrics != true) return
    }

    fun loadSystems() {
    }

    fun onAfterInit() {
        getVersionStatus()
        onLogin()
    }

    fun loadPlannedWork(page: Int) {
        val startDate = when (page) {
            NOW_CHANGES_PAGE -> null
            FUTURE_CHANGES_PAGE -> DateTime.now()
            else -> return
        }
        val finishDate = when (page) {
            NOW_CHANGES_PAGE -> null
            FUTURE_CHANGES_PAGE -> startDate?.plusDays(3)
            else -> return
        }
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

    private fun onLogin() {
//        safeSubscribe {
//            loginUseCase.signIn().subscribe(
//                { },
//                ::handleError
//            )
//        }
    }

    private fun createInitialViewState(): HomeViewState {
        return HomeViewState(
            checkVersionState = LcenState.None,
            checkRestrictionsState = LcenState.None,
            plannedWorkCurrentPage = 0,
        )
    }
}