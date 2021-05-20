package motocitizen.presentation.screens.home

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.google.type.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import motocitizen.data.utils.lastLocation
import motocitizen.domain.lcenstate.isContent
import motocitizen.domain.lcenstate.isError
import motocitizen.domain.lcenstate.isLoading
import motocitizen.domain.model.accident.Accident
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment
import motocitizen.presentation.screens.root.RootActivity

@AndroidEntryPoint
class HomeFragment : VMFragment<HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    private var accidentEpoxyController = AccidentEpoxyController()
    lateinit var rootActivity: RootActivity

    override fun onResume() {
        super.onResume()
        loadAccidentList()
    }

    override fun initUi(savedInstanceState: Bundle?) {
        rootActivity = requireActivity() as RootActivity
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
            loadAccidentList()
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
        loadAccidentList()
    }

    private fun renderContent(list: List<Accident>) {
            accidentEpoxyController.setData(list)
    }

    private fun loadAccidentList() {
        rootActivity.updateLastLocation {
            if (it != null) {
                lastLocation = LatLng.newBuilder()
                    .setLongitude(it.longitude)
                    .setLatitude(it.latitude)
                    .build()
                viewModel.loadAccidentList(
                    lastLocation!!.latitude,
                    lastLocation!!.longitude
                )
            }
        }
    }
}