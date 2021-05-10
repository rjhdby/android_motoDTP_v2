package motocitizen.presentation.screens.accident.create.map

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_map.*
import motocitizen.app.App
import motocitizen.app.utils.DEFAULT_ZOOM
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment
import motocitizen.presentation.screens.root.RootActivity
import timber.log.Timber

@AndroidEntryPoint
class CreateMapFragment : VMFragment<CreateMapViewModel>(R.layout.fragment_create_map),
    OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnCameraMoveStartedListener {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(0.0, 0.0)

    override val viewModel: CreateMapViewModel by viewModels()

    companion object {
        private const val MAP_MIN_ZOOM: Float = 1f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(requireActivity())
    }

    override fun initViewModel() {
    }

    private fun toHome() {
        val root = activity as RootActivity
        root.toHome()
    }

    override fun initUi(savedInstanceState: Bundle?) {
        tool_bar.setTitle(getString(R.string.where))
        tool_bar.run {
            onLeftIconClick = { toHome() }
        }
        (childFragmentManager.findFragmentById(R.id.create_map_container) as SupportMapFragment).getMapAsync(
            this
        )
        ADDRESS.setOnClickListener {
            viewModel.navigateToType()
//            builder.location = AccidentLocation(MyLocationManager.getAddress(latLng), latLng)
//            changeFrameTo(TYPE)
//            EmptyAddressDialog(this, builder.location.address, this::addressDialogCallback)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.setMinZoomPreference(MAP_MIN_ZOOM)
        setLastLocation()

        if (App.locPermission) {
            googleMap.isMyLocationEnabled = true
            setMapListeners()
            viewModel.buildLocationCallBack(googleMap)
            fusedLocationProviderClient.requestLocationUpdates(
                viewModel.locationRequest,
                viewModel.locationCallback,
                requireActivity().mainLooper
            )
        }
    }

    private fun setLastLocation() {
        try {
            if (App.locPermission) {
                val locationResult = fusedLocationProviderClient.lastLocation

                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {

                            googleMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM
                                )
                            )
                        }
                    } else {
                        googleMap.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM)
                        )
                        googleMap.uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Timber.e(e)
        }
    }

    private fun setMapListeners() {
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnCameraMoveStartedListener(this)
    }

    override fun onMyLocationButtonClick(): Boolean {
        viewModel.isBindCam = false
        googleMap.setOnCameraIdleListener {
            googleMap.setOnCameraIdleListener(null)
            viewModel.isBindCam = true
        }
        return false
    }

    override fun onCameraMoveStarted(reason: Int) {
        when (reason) {
            GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE -> {
                viewModel.isBindCam = false
            }
        }
    }
}