package motocitizen.presentation.screens.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.type.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.splash.*
import motocitizen.app.App
import motocitizen.data.gps.LocListener
import motocitizen.data.network.version.VersionStatus
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.lcenstate.isContent
import motocitizen.domain.lcenstate.isError
import motocitizen.domain.lcenstate.isLoading
import motocitizen.domain.model.accident.Accident
import motocitizen.main.R
import motocitizen.presentation.base.showSimpleDialog
import motocitizen.presentation.base.showSimpleDialogWithButton
import motocitizen.presentation.base.viewmodel.VMFragment
import motocitizen.presentation.screens.root.RootActivity
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : VMFragment<HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    private var accidentEpoxyController = AccidentEpoxyController()
    var lastKnownLocation: Location? = null

    fun updateLastLocation() {
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
        try {
            val locationResult = viewModel.fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    lastKnownLocation = task.result
                     if (lastKnownLocation != null) {
                        val latLng: LatLng = LatLng.newBuilder()
                            .setLongitude(lastKnownLocation!!.longitude)
                            .setLatitude(lastKnownLocation!!.latitude)
                            .build()
                        LocListener.currentLocation.value = latLng
                    }
                }
            }
        }
        catch (e: Exception){
            Timber.d(e)
        }
    }


    override fun onResume() {
        super.onResume()
        updateLastLocation()
        viewModel.loadAccidentList()
    }

    override fun initUi(savedInstanceState: Bundle?) {
        recycler_view_home.setController(accidentEpoxyController)
        recycler_view_home.itemAnimator = object : DefaultItemAnimator() {
            override fun animateChange(
                oldHolder: RecyclerView.ViewHolder,
                newHolder: RecyclerView.ViewHolder,
                preInfo: ItemHolderInfo,
                postInfo: ItemHolderInfo,
            ): Boolean {
                return false
            }
        }
        swipe_to_refresh.setOnRefreshListener {
            viewModel.loadAccidentList()
            swipe_to_refresh.isRefreshing = false
        }
    }


    override fun initViewModel() {
        viewModel.onAfterInit()
        viewModel.homeViewState.observe { viewState ->
            renderCheckVersionState(viewState.checkVersionState)
        }
        viewModel.loadAccidentListState.observe {
            show_progress.isVisible = it.isLoading()
            error_view.isVisible = it.isError()
            view_panel.isVisible = it.isContent()
            it.asContentOrNull()?.let(::renderContent)
        }
    }

    private fun renderCheckVersionState(checkVersionState: LcenState<VersionStatus>) {
        val versionStatus = checkVersionState.asContentOrNull() ?: return
        when (versionStatus) {
            VersionStatus.NORMAL -> Unit
            VersionStatus.DEPRECATED -> showSimpleDialogWithButton(R.string.api_version_deprecated)
            VersionStatus.UNSUPPORTED -> showSimpleDialog(
                textResId = R.string.api_version_unsupported,
                cancellable = false
            )
        }
    }

    override fun onStart() {
        super.onStart()

    }

    private fun renderContent(list: List<Accident>) {
        //todo Переделать, т.к. приводит к длительному ожиданию, особенно после запуска приложения
        val start = Date()
        LocListener.currentLocation.observe(viewLifecycleOwner) {
            //todo Убрать после рефакторинга <--
            val end = Date()
            val res = (end.time - start.time) / 1000
            Timber.d("Wait location is: $res sec.")
            //todo Убрать после рефакторинга -->
            accidentEpoxyController.setData(list)
            LocListener.currentLocation.removeObservers(viewLifecycleOwner)
        }
    }
}