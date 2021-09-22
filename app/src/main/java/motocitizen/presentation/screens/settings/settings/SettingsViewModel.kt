package motocitizen.presentation.screens.settings.settings

import android.webkit.CookieManager
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.data.repos.AuthDataRepo
import motocitizen.data.repos.SettingsDataRepo
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.usecases.GetUserUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.delegate
import motocitizen.presentation.base.viewmodel.mapDistinct
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataRepo: SettingsDataRepo,
    private val getUser: GetUserUseCase,
    private val authDataRepo: AuthDataRepo
) : BaseViewModel() {
    private lateinit var tempDepth: String
    private lateinit var tempDistance: String

    private val liveState = MutableLiveData(createInitialViewState())
    private var state: SettingsViewState by liveState.delegate()
    val checkUserState = liveState.mapDistinct { it.checkUserState }

    private fun createInitialViewState(): SettingsViewState {
        return SettingsViewState(checkUserState = LcenState.None)
    }

    fun updateTempDistance() {
        tempDistance = settingsDataRepo.getDistance()
    }

    fun updateTempDepth() {
        tempDepth = settingsDataRepo.getDepth()
    }

    fun setTempDepth() {
        settingsDataRepo.setDepth(tempDepth)
    }

    fun setTempDistance() {
        settingsDataRepo.setDistance(tempDistance)
    }

    fun getDistance(): String {
        return settingsDataRepo.getDistance()
    }

    fun getDepth(): String {
        return settingsDataRepo.getDepth()
    }

    fun loadUser() {
        safeSubscribe {
            getUser(skipCache = true)
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(checkUserState = it) },
                    ::handleError
                )
        }
    }

    fun logOut() {
        clearCookies()
        authDataRepo.clearToken()
    }

    private fun clearCookies() {
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
    }
}