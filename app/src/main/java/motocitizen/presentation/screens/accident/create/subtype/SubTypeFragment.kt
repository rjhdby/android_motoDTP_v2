package motocitizen.presentation.screens.accident.create.subtype

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sub_type.*
import motocitizen.domain.model.accident.AccidentType
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment

@AndroidEntryPoint
class SubTypeFragment : VMFragment<SubTypeViewModel>(R.layout.fragment_sub_type) {

    private val navController by lazy { findNavController() }

    override val viewModel: SubTypeViewModel by viewModels()

    private val args: SubTypeFragmentArgs by navArgs()

    override fun initViewModel() {}

    override fun initUi(savedInstanceState: Bundle?) {
        tool_bar.setTitle(getString(R.string.activity_create_select_type_header))
        tool_bar.run {
            onLeftIconClick = { navController.navigateUp() }
        }
        MOTO_MOTO.setOnClickListener {
            setType(AccidentType.MOTO_MOTO)
        }
        MOTO_MAN.setOnClickListener {
            setType(AccidentType.MOTO_PEDESTRIAN)
        }
        SOLO.setOnClickListener {
            setType(AccidentType.SOLO)
        }
        MOTO_AUTO.setOnClickListener {
            setType(AccidentType.MOTO_AUTO)
        }
    }

    private fun setType(type: AccidentType) {
        navController.navigate(
            SubTypeFragmentDirections.actionCreateSubTypeFragmentToDamageFragment(
                type,
                args.address
            )
        )
    }
}