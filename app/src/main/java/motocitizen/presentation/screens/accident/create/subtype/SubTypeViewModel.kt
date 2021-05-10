package motocitizen.presentation.screens.accident.create.subtype

import androidx.hilt.lifecycle.ViewModelInject
import androidx.navigation.NavController
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address
import motocitizen.presentation.base.viewmodel.BaseViewModel

class SubTypeViewModel @ViewModelInject constructor(
    private val navController: NavController,
) : BaseViewModel() {

    lateinit var address: Address

    fun onAfterInit(address: Address) {
        this.address = address
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun setType(type: AccidentType) {
        navController.navigate(
            SubTypeFragmentDirections.actionCreateSubTypeFragmentToDamageFragment(type, address)
        )
    }
}