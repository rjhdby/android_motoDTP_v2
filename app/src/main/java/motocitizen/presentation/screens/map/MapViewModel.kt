package motocitizen.presentation.screens.map

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel


class MapViewModel @ViewModelInject constructor(
    private val getAccidentUseCase: AccidentUseCase
) : BaseViewModel() {
    var isBindCam = true
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    //todo убрать после реализации входных параметров
    private val token: String = "sasdacxc"
    private val depth: Int = 5
    //todo убрать после реализации входных параметров

    private val _loadAccidentListState = MutableLiveData<LcenState<List<Accident>>>(LcenState.None)
    val loadAccidentListState: LiveData<LcenState<List<Accident>>>
        get() = _loadAccidentListState

    init {
        loadData()
        buildLocationRequest()
    }

    fun loadData() {
        loadAccidentLost()
    }

    private fun loadAccidentLost() {
        safeSubscribe {
            getAccidentUseCase.getAccidentList(token = token, depth = depth)
                .toLcenEventObservable { it }
                .subscribe(
                    _loadAccidentListState::postValue,
                    ::handleError
                )
        }
    }

    fun buildLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 250
        locationRequest.fastestInterval = 100
        locationRequest.smallestDisplacement = 1f
    }

    fun buildLocationCallBack(gMap: GoogleMap) {
        locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult) {
                    if (isBindCam) {
                        val pos = CameraUpdateFactory
                            .newLatLng(
                                LatLng(
                                    locationResult.lastLocation.latitude,
                                    locationResult.lastLocation.longitude
                                )
                            )
                        gMap.animateCamera(pos)
                    }
            }
        }
    }
}