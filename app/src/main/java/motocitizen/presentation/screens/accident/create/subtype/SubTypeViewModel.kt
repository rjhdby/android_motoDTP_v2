package motocitizen.presentation.screens.accident.create.subtype

import androidx.hilt.lifecycle.ViewModelInject
import androidx.navigation.NavController
import motocitizen.domain.model.accident.AccidentType
import motocitizen.presentation.base.viewmodel.BaseViewModel

class SubTypeViewModel @ViewModelInject constructor(
    private val navController: NavController,
) : BaseViewModel() {

    fun navigateUp() {
        navController.navigateUp()
    }

    fun setType(type: AccidentType) {
        navController.navigate(
            SubTypeFragmentDirections.actionCreateSubTypeFragmentToDamageFragment(type)
        )
    }
}