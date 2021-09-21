package motocitizen.presentation.screens.settings.settings

import android.webkit.CookieManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.data.network.user.User
import motocitizen.data.repos.AuthDataRepo
import motocitizen.data.repos.SettingsDataRepo
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.usecases.GetUserUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataRepo: SettingsDataRepo,
    private val getUser: GetUserUseCase,
    private val authDataRepo: AuthDataRepo
) : BaseViewModel() {
    private lateinit var tempDepth: String
    private lateinit var tempDistance: String

    private val _checkRestrictionsState = MutableLiveData<LcenState<User>>(LcenState.None)
    val checkUserState: LiveData<LcenState<User>>
        get() = _checkRestrictionsState

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
                    _checkRestrictionsState::postValue,
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