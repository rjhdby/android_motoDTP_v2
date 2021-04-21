package motocitizen.data.network.accident

import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address

data class CreateAccidentRequest(
    val type: AccidentType,
    val hardness: AccidentHardness,
    val location: Address,
    val description: String,
)