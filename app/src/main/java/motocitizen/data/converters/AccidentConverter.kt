package motocitizen.data.converters

import motocitizen.data.network.accident.AccidentResponse
import motocitizen.data.network.accident.CreateAccidentRequest
import motocitizen.data.utils.getNotNull
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address

object AccidentConverter {
    fun toAccidentList(response: List<AccidentResponse>): List<Accident> {
        val result = getNotNull(response)
        return result.map { accident ->
            Accident(
                id = accident.id,
                created = accident.created,
                updated = accident.updated,
                resolved = accident.resolved,
                verified = accident.verified,
                hidden = accident.hidden,
                description = accident.description,
                conflict = accident.conflict
            )
        }
    }

    fun toAccident(response: AccidentResponse): Accident {
        val result = getNotNull(response)
        return result.let {
            Accident(
                id = it.id,
                created = it.created,
                updated = it.updated,
                resolved = it.resolved,
                verified = it.verified,
                hidden = it.hidden,
                description = it.description,
                conflict = it.conflict
            )
        }
    }

    fun toCreateAccidentRequest(
        type: AccidentType,
        hardness: AccidentHardness,
        location: Address,
        description: String,
    ) =
        CreateAccidentRequest(
            type = type,
            hardness = hardness,
            location = location,
            description = description
        )
}