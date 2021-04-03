package motocitizen.presentation.screens.home

import motocitizen.data.network.restrictions.Restrictions
import motocitizen.data.network.version.VersionStatus
import motocitizen.domain.lcenstate.LcenState

data class HomeViewState(
    val checkVersionState: LcenState<VersionStatus>,
    val checkRestrictionsState: LcenState<Restrictions>,
    val plannedWorkCurrentPage: Int,
)