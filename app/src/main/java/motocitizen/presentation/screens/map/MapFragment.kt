package motocitizen.presentation.screens.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*
import motocitizen.domain.lcenstate.isContent
import motocitizen.domain.lcenstate.isError
import motocitizen.domain.lcenstate.isLoading
import motocitizen.domain.model.accident.Accident
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment
import timber.log.Timber

@AndroidEntryPoint
class MapFragment : VMFragment<MapViewModel>(R.layout.fragment_map) {
    private val REQST_CODE = 100

    override val viewModel: MapViewModel by viewModels()

    override fun initUi(savedInstanceState: Bundle?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        startLocation()


    }

    private fun startLocation() {
        val permissionStatus = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            viewModel.starLocationUpdate()
            viewModel.locationPoint.observe(this, {
                Log.d("startLocation", "latitude= ${it.latitude}, longitude = ${it.longitude}")
            })
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQST_CODE
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    startLocation()
                }
            }
        }


    }

    override fun initViewModel() {
        viewModel.loadAccidentListState.observe {
            show_progress.isVisible = it.isLoading()
            error_view.isVisible = it.isError()
            view_panel.isVisible = it.isContent()
            it.asContentOrNull()?.let(::renderContent)
        }

        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        viewModel.onAfterInit(locationManager)


    }


    private fun renderContent(list: List<Accident>) {
        Timber.i(list.size.toString())
    }
}