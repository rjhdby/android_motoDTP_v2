package motocitizen.presentation.screens.accident.create.type

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_type.*
import motocitizen.domain.model.accident.AccidentType
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment

@AndroidEntryPoint
class CreateTypeFragment : VMFragment<CreateTypeViewModel>(R.layout.fragment_create_type) {
    override val viewModel: CreateTypeViewModel by viewModels()

    private val args: CreateTypeFragmentArgs by navArgs()

    override fun initViewModel() {
        viewModel.onAfterInit(args.address)
    }

    override fun initUi(savedInstanceState: Bundle?) {
        tool_bar.setTitle(getString(R.string.activity_create_select_type_header))
        tool_bar.run {
            onLeftIconClick = viewModel::navigateUp
        }
        other_type.setOnClickListener {
            viewModel.setType(AccidentType.OTHER)
        }
        steal_type.setOnClickListener {
            viewModel.setType(AccidentType.STEAL)
        }
        break_type.setOnClickListener {
            viewModel.setType(AccidentType.BREAK)
        }
        accident_type.setOnClickListener {
            viewModel.setType(AccidentType.MOTO_AUTO)
        }
    }
}