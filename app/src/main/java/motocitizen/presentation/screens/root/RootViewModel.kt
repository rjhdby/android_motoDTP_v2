package motocitizen.presentation.screens.root

import android.location.LocationManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import motocitizen.data.network.user.User
import motocitizen.data.repos.AuthDataRepo
import motocitizen.data.storage.keyvalue.SharedPrefsKey
import motocitizen.data.storage.keyvalue.SharedPrefsStorageImpl
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.usecases.GetUserUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.commands.VMCommand

class RootViewModel @ViewModelInject constructor(
    private val getUser: GetUserUseCase,
    private val authDataRepo: AuthDataRepo
) : BaseViewModel() {

    companion object {
        const val LOC_MIN_TIME_UPDATE = 1000L
        const val LOC_MIN_DISTANCE = 1F
    }

    private val _checkRestrictionsState = MutableLiveData<LcenState<User>>(LcenState.None)
    val checkUserState: LiveData<LcenState<User>>
        get() = _checkRestrictionsState

    private lateinit var locationManager: LocationManager

    fun onAfterInit(locManager: LocationManager) {
        checkClientCertificate()
        // todo Со старого проекта, вероятно не понадобится.
        locationManager = locManager
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

    fun getToken(): String? {
        return authDataRepo.getToken()
    }

    private fun checkClientCertificate() {
    }

    fun onAliasChosen(alias: String?) {
    }

    fun forceChooseCertificate() {
    }
}

object ChooseCertificate : VMCommand
object ForceChooseCertificate : VMCommand