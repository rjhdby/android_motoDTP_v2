package motocitizen.presentation.screens.settings.settings

import motocitizen.data.network.user.User
import motocitizen.domain.lcenstate.LcenState

data class SettingsViewState(
    val checkUserState: LcenState<User>
)