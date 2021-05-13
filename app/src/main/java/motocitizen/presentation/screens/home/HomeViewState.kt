package motocitizen.presentation.screens.home

import motocitizen.data.network.user.User
import motocitizen.domain.lcenstate.LcenState

data class HomeViewState(
    val checkUserState: LcenState<User>,
)