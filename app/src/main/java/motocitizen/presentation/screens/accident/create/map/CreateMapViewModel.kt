package motocitizen.presentation.screens.accident.create.map

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.usecases.NominationUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.base.viewmodel.delegate
import motocitizen.presentation.base.viewmodel.mapDistinct
import motocitizen.presentation.screens.map.MapViewModel
import javax.inject.Inject

@HiltViewModel
class CreateMapViewModel @Inject constructor(
    private val nominationUseCase: NominationUseCase,
    val fusedLocationProviderClient: FusedLocationProviderClient
) : BaseViewModel() {

    private val liveState = MutableLiveData(createInitialViewState())
    private var state: CreateMapViewState by liveState.delegate()

    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    var isBindCam = true
    var requestingLocationUpdates = false
    val loadAddressState = liveState.mapDistinct { it.loadAddress }

    private fun createInitialViewState(): CreateMapViewState {
        return CreateMapViewState(loadAddress = LcenState.None)
    }

    init {
        buildLocationRequest()
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = MapViewModel.LOC_REQUEST_INTERVAL
        locationRequest.fastestInterval = MapViewModel.LOC_REQUEST_FAST_INTERVAL
        locationRequest.smallestDisplacement = MapViewModel.LOC_REQUEST_SMALLEST_DISTANCE
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

    fun searchAddress(lat: Double, lon: Double) {
        safeSubscribe {
            nominationUseCase(lat, lon)
                .toLcenEventObservable { it }
                .subscribe(
                    { state = state.copy(loadAddress = it) },
                    this::handleError
                )
        }
    }
}