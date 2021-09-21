package motocitizen.presentation.screens.home

import motocitizen.data.network.user.User
import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.model.accident.Accident

data class HomeViewState(
    val accidentList: LcenState<List<Accident>>,
    val user: LcenState<User>
)