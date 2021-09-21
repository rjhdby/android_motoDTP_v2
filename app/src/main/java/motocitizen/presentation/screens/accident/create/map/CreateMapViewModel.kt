package motocitizen.presentation.screens.accident.create.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Address
import motocitizen.domain.usecases.NominationUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel
import motocitizen.presentation.screens.map.MapViewModel
import okhttp3.ResponseBody

class CreateMapViewModel @ViewModelInject constructor(
    private val navController: NavController,
    private val nominationUseCase: NominationUseCase,
    val fusedLocationProviderClient: FusedLocationProviderClient
) : BaseViewModel() {

    var isBindCam = true
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    var requestingLocationUpdates = false

    private val _loadAddress = MutableLiveData<LcenState<ResponseBody>>(
        LcenState.None
    )
    val loadAddress: LiveData<LcenState<ResponseBody>>
        get() = _loadAddress

    fun navigateToType(address: Address) {
        navController.navigate(
            CreateMapFragmentDirections.actionCreateMapFragmentToCreateTypeFragment(address)
        )
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
                    _loadAddress::postValue,
                    this::handleError
                )
        }
    }
}