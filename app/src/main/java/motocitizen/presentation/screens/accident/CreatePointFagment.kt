package motocitizen.presentation.screens.accident

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import motocitizen.data.network.version.VersionStatus
import motocitizen.domain.lcenstate.LcenState
import motocitizen.main.R
import motocitizen.presentation.base.showSimpleDialog
import motocitizen.presentation.base.showSimpleDialogWithButton
import motocitizen.presentation.base.viewmodel.VMFragment

@AndroidEntryPoint
class CreatePointFagment : VMFragment<CreatePointViewModel>(R.layout.fragment_create_point) {

    override val viewModel:CreatePointViewModel by viewModels()

    override fun initUi(savedInstanceState: Bundle?) {
    }

    override fun initViewModel() {
    }
}