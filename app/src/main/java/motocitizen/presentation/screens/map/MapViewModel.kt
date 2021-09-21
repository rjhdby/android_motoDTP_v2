package motocitizen.presentation.screens.map

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
import motocitizen.data.network.user.User
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.toLcenEventObservable
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.usecases.AccidentUseCase
import motocitizen.domain.usecases.GetUserUseCase
import motocitizen.presentation.base.viewmodel.BaseViewModel

class MapViewModel @ViewModelInject constructor(
    private val getAccidentUseCase: AccidentUseCase,
    private val navController: NavController,
    private val getUser: GetUserUseCase,
    val fusedLocationProviderClient: FusedLocationProviderClient
) : BaseViewModel() {

    //todo Вынести в общий object для обоих карт
    companion object {
        const val LOC_REQUEST_INTERVAL = 500L
        const val LOC_REQUEST_FAST_INTERVAL = 250L
        const val LOC_REQUEST_SMALLEST_DISTANCE = 1f
    }
    var requestingLocationUpdates = false
    var isBindCam = true
    var accident: Accident? = null

    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var user: User

    //todo убрать после реализации входных параметров
    private val depth: Int = 999

    private val _loadAccidentListState = MutableLiveData<LcenState<List<Accident>>>(LcenState.None)
    val loadAccidentListState: LiveData<LcenState<List<Accident>>>
        get() = _loadAccidentListState

    init {
        buildLocationRequest()
    }

    fun loadData() {
        if (accident == null) {
            safeSubscribe {
                getAccidentUseCase.getAccidentList(
                    depth = depth,
                    userId = userState.value!!.asContent().id
                )
                    .toLcenEventObservable { it }
                    .subscribe(
                        _loadAccidentListState::postValue,
                        ::handleError
                    )
            }
        } else {
            val list = mutableListOf<Accident>()
            list.add(accident!!)
            _loadAccidentListState.postValue(LcenState.Content(list))
        }
    }

    fun onAfterInit(accident: Accident) {
        this.accident = accident
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
                if (isBindCam && accident == null) {
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

    fun toDetails(accidentId: String) {
        navController.navigate(
            MapFragmentDirections.actionMapFragmentToAccidentDetailsFragment(
                accidentId = accidentId,
                user = userState.value!!.asContent(),
                mapEnable = false
            )
        )
    }

    private val _userState = MutableLiveData<LcenState<User>>(LcenState.None)
    val userState: LiveData<LcenState<User>>
        get() = _userState

    fun loadUser() {
        safeSubscribe {
            getUser(skipCache = false)
                .toLcenEventObservable { it }
                .subscribe(
                    _userState::postValue,
                    ::handleError
                )
        }
    }
}