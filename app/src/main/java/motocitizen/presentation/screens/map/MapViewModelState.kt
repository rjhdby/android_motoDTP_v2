package motocitizen.presentation.screens.map

import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.model.accident.Accident

data class MapViewModelState(
    val loadAccidentListState: LcenState<List<Accident>>
)