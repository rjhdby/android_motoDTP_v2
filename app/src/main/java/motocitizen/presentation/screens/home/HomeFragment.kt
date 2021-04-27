package motocitizen.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import motocitizen.data.gps.LocListener
import motocitizen.data.network.version.VersionStatus
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.model.accident.Accident
import motocitizen.main.R
import motocitizen.presentation.base.showSimpleDialog
import motocitizen.presentation.base.showSimpleDialogWithButton
import motocitizen.presentation.base.viewmodel.VMFragment
import motocitizen.presentation.screens.root.RootActivity

@AndroidEntryPoint
class HomeFragment : VMFragment<HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    private var accidentEpoxyController = AccidentEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.loadRestrictions()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAccidentsList()
    }

    override fun initUi(savedInstanceState: Bundle?) {
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
            viewModel.loadRestrictions()
            swipe_to_refresh.isRefreshing = false
        }
    }

    override fun initViewModel() {
        viewModel.onAfterInit()
        viewModel.homeViewState.observe { viewState ->
            renderCheckVersionState(viewState.checkVersionState)
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
        observeLocation()
    }

    private fun showAccidentsList() {
        viewModel.accidentList.observe(viewLifecycleOwner) { accidents ->
            accidentEpoxyController.setAccidents(accidents as MutableList<Accident>)
            LocListener.currentLocation.observe(viewLifecycleOwner) {
                recycler_view_home.setControllerAndBuildModels(accidentEpoxyController)
                LocListener.currentLocation.removeObservers(viewLifecycleOwner)
            }
        }
    }

    private fun observeLocation() {
        val activity = requireActivity() as RootActivity
        activity.viewModel.observeLocation(viewLifecycleOwner, { locPoint ->

        })
    }
}