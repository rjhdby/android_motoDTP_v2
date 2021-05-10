package motocitizen.presentation.screens.root

import android.annotation.SuppressLint
import android.location.Criteria
import android.location.LocationManager
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import motocitizen.data.gps.LocListener
import motocitizen.data.gps.LocationPoint
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

    private val locationPoint = MutableLiveData<LocationPoint>()
    private lateinit var locationManager: LocationManager
    private val locationListener = LocListener()

    @SuppressLint("MissingPermission")
    //todo переделать LocListener на FusedLocationProviderClient
    fun starLocationUpdate() {
        locationListener.setLiveData(locationPoint)
        val provider: String? = locationManager.getBestProvider(Criteria(), true)

        locationManager.requestLocationUpdates(
            provider!!,
            LOC_MIN_TIME_UPDATE,
            LOC_MIN_DISTANCE,
            locationListener
        )
    }

    fun onAfterInit(locManager: LocationManager) {
        checkClientCertificate()
        // todo Со старого проекта, вероятно не понадобится.
        //loadRestrictions()
        locationManager = locManager
    }

    fun observeLocation(owner: LifecycleOwner, observe: (LocationPoint) -> Unit) {
        locationPoint.observe(owner) { locPoint ->
            observe(locPoint)
        }
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