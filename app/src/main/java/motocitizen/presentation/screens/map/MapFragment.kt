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
import motocitizen.presentation.screens.root.RootActivity
import timber.log.Timber

@AndroidEntryPoint
class MapFragment : VMFragment<MapViewModel>(R.layout.fragment_map) {


    override val viewModel: MapViewModel by viewModels()

    override fun initUi(savedInstanceState: Bundle?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        observeLocation()

    }

    private fun observeLocation() {
        val activity = requireActivity() as RootActivity
        activity.viewModel.observeLocation(this,{locPoint ->
            Log.d(
                "observeLocation",
                "MapFragment longitude= ${locPoint.longitude}, latitude = ${locPoint.latitude}"
            )

        })
    }


    override fun initViewModel() {
        viewModel.loadAccidentListState.observe {
            show_progress.isVisible = it.isLoading()
            error_view.isVisible = it.isError()
            view_panel.isVisible = it.isContent()
            it.asContentOrNull()?.let(::renderContent)
        }


    }


    private fun renderContent(list: List<Accident>) {
        Timber.i(list.size.toString())
    }
}