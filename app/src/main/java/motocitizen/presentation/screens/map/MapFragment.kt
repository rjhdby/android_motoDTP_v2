package motocitizen.presentation.screens.map

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*
import motocitizen.app.App
import motocitizen.app.utils.DEFAULT_ZOOM
import motocitizen.app.utils.accidentMarker
import motocitizen.domain.lcenstate.isContent
import motocitizen.domain.lcenstate.isError
import motocitizen.domain.lcenstate.isLoading
import motocitizen.domain.model.accident.Accident
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment
import motocitizen.presentation.base.viewmodel.commands.VMCommand
import motocitizen.presentation.screens.root.RootActivity
import timber.log.Timber
import java.util.*

//todo Вынести в общий object для обоих карт
private const val MSC_CENTER_LAT = 55.75375094653797
private const val MSC_CENTER_LON = 37.62135415470559
private const val MAP_MIN_ZOOM: Float = 1f

@AndroidEntryPoint
class MapFragment : VMFragment<MapViewModel>(R.layout.fragment_map), OnMapReadyCallback,
    OnMarkerClickListener, OnMyLocationButtonClickListener, OnCameraMoveStartedListener {

    private val navController by lazy { findNavController() }

    override val viewModel: MapViewModel by viewModels()
    private val args: MapFragmentArgs by navArgs()

    private lateinit var googleMap: GoogleMap

    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(MSC_CENTER_LAT, MSC_CENTER_LON)
    private val markers = HashMap<String, String>()
    private var selected: String = ""

    override fun initUi(savedInstanceState: Bundle?) {
        (childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment).getMapAsync(
            this
        )
    }

    override fun initViewModel() {
        viewModel.userState.observe {
            it.asContentOrNull()?.let { viewModel.loadData() }
        }
        viewModel.loadAccidentListState.observe {
            show_progress.isVisible = it.isLoading()
            error_view.isVisible = it.isError()
            view_panel.isVisible = it.isContent()
            it.asContentOrNull()?.let(::renderContent)
        }
        if (arguments != null) { //note Был ли переход с AccidentDetailsFragment
            viewModel.onAfterInit(args.accident!!)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUser()
        if (viewModel.requestingLocationUpdates) {
            startLocationUpdates()
        }
    }

    private fun renderContent(list: List<Accident>) {
        if (::googleMap.isInitialized) {
            googleMap.clear()
            for (accident in list) {
                markers[googleMap.accidentMarker(accident).id] = accident.id
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.setMinZoomPreference(MAP_MIN_ZOOM)
        setLastLocation()
        if (App.isLocPermission) {
            googleMap.isMyLocationEnabled = true
            setMapListeners()
            viewModel.buildLocationCallBack(googleMap)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.fusedLocationProviderClient.removeLocationUpdates(viewModel.locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        viewModel.fusedLocationProviderClient.requestLocationUpdates(
            viewModel.locationRequest,
            viewModel.locationCallback,
            requireActivity().mainLooper
        )
        viewModel.requestingLocationUpdates = true
    }

    private fun setMapListeners() {
        googleMap.setOnMarkerClickListener(this)
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnCameraMoveStartedListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        viewModel.isBindCam = false
        if (arguments == null && selected == marker.id && markers.containsKey(marker.id)) {
            viewModel.toDetails(markers[marker.id]!!)
        } else {
            marker.showInfoWindow()
            selected = marker.id
        }
        return true
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
            OnCameraMoveStartedListener.REASON_GESTURE -> {
                viewModel.isBindCam = false
            }
        }
    }

    private fun setLastLocation() {
        if (arguments != null) { //note Был ли переход с AccidentDetailsFragment
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        args.accident!!.location.lat,
                        args.accident!!.location.lon
                    ), DEFAULT_ZOOM
                )
            )
        } else {
            try {
                if (App.isLocPermission && (requireActivity() as RootActivity).checkGpsEnable()) {
                    val locationResult = viewModel.fusedLocationProviderClient.lastLocation
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
                } else {
                    googleMap.moveCamera(
                        CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM)
                    )
                    googleMap.uiSettings.isMyLocationButtonEnabled = false
                }
            } catch (e: SecurityException) {
                Timber.e(e)
            }
        }
    }

    override fun onCommandReceived(command: VMCommand) {
        super.onCommandReceived(command)
        when (command) {
            is ToDetails -> navController.navigate(
                MapFragmentDirections.actionMapFragmentToAccidentDetailsFragment(
                    accidentId = command.accidentId,
                    user = command.user,
                    mapEnable = command.mapEnable
                )
            )

        }
    }
}