package motocitizen.presentation.screens.root

import android.location.LocationManager
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.data.repos.AuthDataRepo
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.usecases.GetUserUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.commands.VMCommand
import motocitizen.presentation.base.viewmodel.delegate
import motocitizen.presentation.base.viewmodel.mapDistinct
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val authDataRepo: AuthDataRepo,
    var fusedLocationProviderClient: FusedLocationProviderClient
) : BaseViewModel() {

    private lateinit var locationManager: LocationManager

    private val liveState = MutableLiveData(createInitialViewState())
    private var state: RootViewState by liveState.delegate()
    val checkUserState = liveState.mapDistinct { it.checkUserState }

    private fun createInitialViewState(): RootViewState {
        return RootViewState(checkUserState = LcenState.None)
    }

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
                    { state = state.copy(checkUserState = it) },
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