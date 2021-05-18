package motocitizen.presentation.screens.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.google.type.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import motocitizen.app.App
import motocitizen.data.utils.lastLocation
import motocitizen.domain.lcenstate.isContent
import motocitizen.domain.lcenstate.isError
import motocitizen.domain.lcenstate.isLoading
import motocitizen.domain.model.accident.Accident
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment
import motocitizen.presentation.screens.root.RootActivity
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : VMFragment<HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    private var accidentEpoxyController = AccidentEpoxyController()
    private var lastKnownLocation: Location? = null

    private fun updateLastLocation() {
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
                        lastLocation.value = latLng
                    }
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAccidentList()
    }

    override fun initUi(savedInstanceState: Bundle?) {
        recycler_view_home.setController(accidentEpoxyController)
        accidentEpoxyController.clickListener = viewModel::onItemPressed
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
            updateLastLocation()
            viewModel.loadAccidentList()
            swipe_to_refresh.isRefreshing = false
        }
    }

    override fun initViewModel() {
        updateLastLocation()
        viewModel.loadAccidentListState.observe {
            show_progress.isVisible = it.isLoading()
            error_view.isVisible = it.isError()
            view_panel.isVisible = it.isContent()
            it.asContentOrNull()?.let(::renderContent)
        }
    }

    private fun renderContent(list: List<Accident>) {
        lastLocation.observe(this) {
            if ((requireActivity() as RootActivity).checkGpsEnable() && App.isLocPermission) {
                accidentEpoxyController.setData(list)
            }
        }
    }
}