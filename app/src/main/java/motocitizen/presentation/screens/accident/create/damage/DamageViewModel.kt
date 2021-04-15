package motocitizen.presentation.screens.accident.create.damage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.navigation.NavController
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.presentation.base.viewmodel.BaseViewModel

class DamageViewModel @ViewModelInject constructor(
    private val navController: NavController,
) : BaseViewModel() {

    lateinit var type: AccidentType

    fun onAfterInit(type: AccidentType){
        this.type = type
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun setMedicine(medicine: AccidentHardness) {
        navController.navigate(
            DamageFragmentDirections.actionCreateDamageFragmentToCreateDescriptionFragment(type, medicine)
        )
    }
}
