package motocitizen.presentation.screens.accident.create.damage

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_people.*
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.main.R
import motocitizen.presentation.base.viewmodel.VMFragment

@AndroidEntryPoint
class DamageFragment : VMFragment<DamageViewModel>(R.layout.fragment_create_people) {

    private val navController by lazy { findNavController() }

    private val args: DamageFragmentArgs by navArgs()

    override val viewModel: DamageViewModel by viewModels()

    override fun initViewModel() {
    }

    override fun initUi(savedInstanceState: Bundle?) {
        tool_bar.setTitle(getString(R.string.activity_create_people_header))
        tool_bar.run {
            onLeftIconClick = { navController.navigateUp() }
        }

        PEOPLE_LETHAL.setOnClickListener { setMedicine(AccidentHardness.LETHAL) }
        PEOPLE_HEAVY.setOnClickListener { setMedicine(AccidentHardness.HEAVY) }
        PEOPLE_LIGHT.setOnClickListener { setMedicine(AccidentHardness.LIGHT) }
        PEOPLE_OK.setOnClickListener { setMedicine(AccidentHardness.NO) }
        PEOPLE_UNKNOWN.setOnClickListener { setMedicine(AccidentHardness.UNKNOWN) }
    }

    private fun setMedicine(medicine: AccidentHardness) {
        navController.navigate(
            DamageFragmentDirections.actionCreateDamageFragmentToCreateDescriptionFragment(
                args.type,
                medicine,
                args.address
            )
        )
    }
}