package motocitizen.presentation.screens.accident.create.damage

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_people.*
import kotlinx.android.synthetic.main.fragment_create_people.tool_bar
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment

@AndroidEntryPoint
class DamageFragment : VMFragment<DamageViewModel>(R.layout.fragment_create_people) {

    private val args: DamageFragmentArgs by navArgs()

    override val viewModel: DamageViewModel by viewModels()

    override fun initViewModel() {
        viewModel.onAfterInit(args.type, args.address)
    }

    override fun initUi(savedInstanceState: Bundle?) {
        tool_bar.setTitle(getString(R.string.activity_create_people_header))
        tool_bar.run {
            onLeftIconClick = viewModel::navigateUp
        }

        PEOPLE_LETHAL.setOnClickListener { viewModel.setMedicine(AccidentHardness.LETHAL) }
        PEOPLE_HEAVY.setOnClickListener { viewModel.setMedicine(AccidentHardness.HEAVY) }
        PEOPLE_LIGHT.setOnClickListener { viewModel.setMedicine(AccidentHardness.LIGHT) }
        PEOPLE_OK.setOnClickListener { viewModel.setMedicine(AccidentHardness.NO) }
        PEOPLE_UNKNOWN.setOnClickListener { viewModel.setMedicine(AccidentHardness.UNKNOWN) }
    }
}