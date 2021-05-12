package motocitizen.presentation.screens.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
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
    private val getAccidentUseCase: AccidentUseCase,
    var fusedLocationProviderClient: FusedLocationProviderClient
) : BaseViewModel() {
    companion object{
        const val LOC_REQUEST_INTERVAL = 500L
        const val LOC_REQUEST_FAST_INTERVAL = 250L
        const val LOC_REQUEST_SMALLEST_DISTANCE = 1f
    }
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
        loadAccidentList()
    }

    private fun loadAccidentList() {
        safeSubscribe {
            getAccidentUseCase.getAccidentList(depth = depth)
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
        locationRequest.interval = LOC_REQUEST_INTERVAL
        locationRequest.fastestInterval = LOC_REQUEST_FAST_INTERVAL
        locationRequest.smallestDisplacement = LOC_REQUEST_SMALLEST_DISTANCE
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