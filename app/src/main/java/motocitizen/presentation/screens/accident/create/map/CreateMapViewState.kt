package motocitizen.presentation.screens.accident.create.map

import motocitizen.domain.lcenstate.LcenState
import okhttp3.ResponseBody

data class CreateMapViewState(
    val loadAddress: LcenState<ResponseBody>
)