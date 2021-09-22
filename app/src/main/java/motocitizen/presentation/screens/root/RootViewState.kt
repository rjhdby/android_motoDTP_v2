package motocitizen.presentation.screens.root

import motocitizen.data.network.user.User
import motocitizen.domain.lcenstate.LcenState

data class RootViewState(
    val checkUserState: LcenState<User>
)