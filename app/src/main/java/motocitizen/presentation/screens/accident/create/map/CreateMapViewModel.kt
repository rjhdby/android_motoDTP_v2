package motocitizen.presentation.screens.accident.create.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.navigation.NavController
import motocitizen.presentation.base.viewmodel.BaseViewModel

class CreateMapViewModel @ViewModelInject constructor(
    private val navController: NavController,
) : BaseViewModel() {

    fun navigateToType() {
        navController.navigate(
            CreateMapFragmentDirections.actionCreateMapFragmentToCreateTypeFragment()
        )
    }
}