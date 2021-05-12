package motocitizen.presentation.screens.root

import android.location.LocationManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import motocitizen.data.network.restrictions.Restrictions
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.usecases.GetRestrictionsUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.commands.VMCommand

class RootViewModel @ViewModelInject constructor(
    private val getRestrictions: GetRestrictionsUseCase,
) : BaseViewModel() {

    companion object {
        const val LOC_MIN_TIME_UPDATE = 1000L
        const val LOC_MIN_DISTANCE = 1F
    }

    private val _checkRestrictionsState = MutableLiveData<LcenState<Restrictions>>(LcenState.None)
    val checkRestrictionsState: LiveData<LcenState<Restrictions>>
        get() = _checkRestrictionsState

    private lateinit var locationManager: LocationManager

    fun onAfterInit(locManager: LocationManager) {
        checkClientCertificate()
        // todo Со старого проекта, вероятно не понадобится.
        //loadRestrictions()
        locationManager = locManager
    }

    private fun loadRestrictions() {
        safeSubscribe {
            getRestrictions(skipCache = true)
                .toLcenEventObservable { it }
                .subscribe(
                    _checkRestrictionsState::postValue,
                    ::handleError
                )
        }
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