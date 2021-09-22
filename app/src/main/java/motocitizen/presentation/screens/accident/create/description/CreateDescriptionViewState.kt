package motocitizen.presentation.screens.accident.create.description

import motocitizen.domain.lcenstate.LcenState
import okhttp3.ResponseBody

data class CreateDescriptionViewState(
    val newAccident: LcenState<ResponseBody>
)