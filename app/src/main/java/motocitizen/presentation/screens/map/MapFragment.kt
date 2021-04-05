package motocitizen.presentation.screens.map

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
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
    override val viewModel: MapViewModel by viewModels()

    override fun initUi(savedInstanceState: Bundle?) {
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