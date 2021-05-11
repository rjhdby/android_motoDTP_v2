package motocitizen.presentation.screens.accident.create.damage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.navigation.NavController
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address
import motocitizen.presentation.base.viewmodel.BaseViewModel

class DamageViewModel @ViewModelInject constructor(
    private val navController: NavController,
) : BaseViewModel() {

    lateinit var type: AccidentType
    lateinit var address: Address

    fun onAfterInit(type: AccidentType, address: Address){
        this.type = type
        this.address = address
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun setMedicine(medicine: AccidentHardness) {
        navController.navigate(
            DamageFragmentDirections.actionCreateDamageFragmentToCreateDescriptionFragment(type, medicine, address)
        )
    }
}
