package motocitizen.presentation.screens.accident.create.type

import androidx.hilt.lifecycle.ViewModelInject
import androidx.navigation.NavController
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address
import motocitizen.presentation.base.viewmodel.BaseViewModel

class CreateTypeViewModel @ViewModelInject constructor(
    private val navController: NavController,
) : BaseViewModel() {

    lateinit var address: Address

    fun onAfterInit(address: Address){
        this.address = address
    }

    fun setType(type: AccidentType) {
        if (type.isAccident()) {
            navController.navigate(
                CreateTypeFragmentDirections.actionCreateTypeFragmentToCreateSubTypeFragment(address)
            )
        } else {
            navController.navigate(
                CreateTypeFragmentDirections.actionCreateTypeFragmentToCreateDescriptionFragment(
                    type,
                    AccidentHardness.NO,
                    address
                )
            )
        }
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}
