package motocitizen.presentation.screens.home

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
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

@AndroidEntryPoint
class HomeFragment : VMFragment<HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    private var accidentEpoxyController = AccidentEpoxyController()

    override fun onResume() {
        super.onResume()
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
        viewModel.loadAccidentListState.observe {
            show_progress.isVisible = it.isLoading()
            error_view.isVisible = it.isError()
            view_panel.isVisible = it.isContent()
            it.asContentOrNull()?.let(::renderContent)
        }
    }

    override fun onStart() {
        super.onStart()
        observeLocation()
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

    private fun observeLocation() {
        val activity = requireActivity() as RootActivity
        activity.viewModel.observeLocation(viewLifecycleOwner, { locPoint ->
            Timber.d("Location is: ${locPoint.latitude} x ${locPoint.longitude}")
        })
    }
}