package motocitizen.presentation.screens.map

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
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
import timber.log.Timber

@AndroidEntryPoint
class MapFragment : VMFragment<MapViewModel>(R.layout.fragment_map) {

    override val viewModel: MapViewModel by viewModels()
    private lateinit var googleMap: GoogleMap
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(0.0, 0.0)

    companion object {
        private const val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
        private const val MAP_MIN_ZOOM: Float = 1f
    }

    override fun initUi(savedInstanceState: Bundle?) {
        (childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment).getMapAsync(
            this
        )
    }

//    private fun observeLocation() {
//        val activity = requireActivity() as RootActivity
//        activity.viewModel.observeLocation(this,{locPoint ->
//
//        })
//    }

    override fun initViewModel() {
        viewModel.loadAccidentListState.observe {
            show_progress.isVisible = it.isLoading()
            error_view.isVisible = it.isError()
            view_panel.isVisible = it.isContent()
            it.asContentOrNull()?.let(::renderContent)
        }
    }
    override fun onResume() {
        super.onResume()
        if (::googleMap.isInitialized) {
            showAccidentsMarkers()
        }
    }

    private fun renderContent(list: List<Accident>) {
        Timber.i(list.size.toString())
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

        showAccidentsMarkers()
    }

    private fun setMapListeners() {
        googleMap.setOnMarkerClickListener(this)
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnCameraMoveStartedListener(this)
    }

    private fun showAccidentsMarkers() {
        googleMap.clear()
        viewModel.loadAccidentListState.observe(viewLifecycleOwner) { accidents ->
            if (accidents.isContent()) {
                for (accident in accidents.asContent()) {
                    googleMap.accidentMarker(accident)
                }
            }
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        viewModel.isBindCam = false
        return false
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
}