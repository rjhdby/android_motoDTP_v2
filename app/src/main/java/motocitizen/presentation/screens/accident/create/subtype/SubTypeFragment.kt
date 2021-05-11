package motocitizen.presentation.screens.accident.create.subtype

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sub_type.*
import kotlinx.android.synthetic.main.fragment_sub_type.tool_bar
import motocitizen.domain.model.accident.AccidentType
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment

@AndroidEntryPoint
class SubTypeFragment : VMFragment<SubTypeViewModel>(R.layout.fragment_sub_type) {

    override val viewModel: SubTypeViewModel by viewModels()

    private val args: SubTypeFragmentArgs by navArgs()

    override fun initViewModel() {
        viewModel.onAfterInit(args.address)
    }

    override fun initUi(savedInstanceState: Bundle?) {
        tool_bar.setTitle(getString(R.string.activity_create_select_type_header))
        tool_bar.run {
            onLeftIconClick = viewModel::navigateUp
        }
        MOTO_MOTO.setOnClickListener {
            viewModel.setType(AccidentType.MOTO_MOTO)
        }
        MOTO_MAN.setOnClickListener {
            viewModel.setType(AccidentType.MOTO_PEDESTRIAN)
        }
        SOLO.setOnClickListener {
            viewModel.setType(AccidentType.SOLO)
        }
        MOTO_AUTO.setOnClickListener {
            viewModel.setType(AccidentType.MOTO_AUTO)
        }
    }
}