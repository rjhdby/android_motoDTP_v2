package motocitizen.presentation.screens.accident.details

import motocitizen.domain.lcenstate.LcenState
import motocitizen.domain.model.accident.Accident

data class AccidentDetailsViewState(
    val loadAccident: LcenState<Accident>
)