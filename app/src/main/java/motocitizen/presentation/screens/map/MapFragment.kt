package motocitizen.presentation.screens.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*
import motocitizen.app.utils.accidentMarker
import motocitizen.domain.lcenstate.isContent
import motocitizen.domain.lcenstate.isError
import motocitizen.domain.lcenstate.isLoading
import motocitizen.domain.model.accident.Accident
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment
import timber.log.Timber

@AndroidEntryPoint
class MapFragment : VMFragment<MapViewModel>(R.layout.fragment_map), OnMapReadyCallback,
    OnMarkerClickListener, OnMyLocationButtonClickListener, OnCameraMoveStartedListener {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override val viewModel: MapViewModel by viewModels()
    private lateinit var googleMap: GoogleMap

    companion object {
        private const val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
        private const val MAP_MIN_ZOOM: Float = 7f
    }

    override fun initUi(savedInstanceState: Bundle?) {
        (childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment).getMapAsync(
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(requireActivity())
    }

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

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setMinZoomPreference(MAP_MIN_ZOOM)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        googleMap.isMyLocationEnabled = true
        setMapListeners(googleMap)
        viewModel.buildLocationCallBack(googleMap)
        fusedLocationProviderClient.requestLocationUpdates(
            viewModel.locationRequest,
            viewModel.locationCallback,
            null
        )

        showAccidentsMarkers()
//        val cameraUpdate = CameraUpdateFactory.newLatLng()
//        googleMap.moveCamera(cameraUpdate)
//        showAccidentsMarkers()
    }

    private fun setMapListeners(gMap: GoogleMap) {
        googleMap = gMap
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
//        googleMap.stopAnimation()
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
}