package motocitizen.presentation.screens.accident.create.type

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_type.*
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment

@AndroidEntryPoint
class CreateTypeFragment : VMFragment<CreateTypeViewModel>(R.layout.fragment_create_type) {

    private val navController by lazy { findNavController() }

    override val viewModel: CreateTypeViewModel by viewModels()

    private val args: CreateTypeFragmentArgs by navArgs()

    override fun initViewModel() {
    }

    override fun initUi(savedInstanceState: Bundle?) {
        tool_bar.setTitle(getString(R.string.activity_create_select_type_header))
        tool_bar.run {
            onLeftIconClick = { navController.navigateUp() }
        }
        other_type.setOnClickListener {
            setType(AccidentType.OTHER)
        }
        steal_type.setOnClickListener {
            setType(AccidentType.STEAL)
        }
        break_type.setOnClickListener {
            setType(AccidentType.BREAK)
        }
        accident_type.setOnClickListener {
            setType(AccidentType.MOTO_AUTO)
        }
    }

    private fun setType(type: AccidentType) {
        if (type.isAccident()) {
            navController.navigate(
                CreateTypeFragmentDirections.actionCreateTypeFragmentToCreateSubTypeFragment(args.address)
            )
        } else {
            navController.navigate(
                CreateTypeFragmentDirections.actionCreateTypeFragmentToCreateDescriptionFragment(
                    type,
                    //todo #69 Переделать когда Custom Enum supports null values
                    AccidentHardness.NULL,
                    args.address
                )
            )
        }
    }
}