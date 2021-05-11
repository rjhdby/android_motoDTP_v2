package motocitizen.presentation.screens.home

import motocitizen.data.network.restrictions.Restrictions
import motocitizen.domain.lcenstate.LcenState

data class HomeViewState(
    val checkRestrictionsState: LcenState<Restrictions>,
)