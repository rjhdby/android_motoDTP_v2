package motocitizen.data.converters

import motocitizen.data.network.accident.AccidentResponse
import motocitizen.data.utils.getNotNull
import motocitizen.domain.model.accident.Accident

object AccidentConverter {
    fun toAccidentList(response: List<AccidentResponse>): List<Accident> {
        val result = getNotNull(response)
        return result.map { accident ->
            Accident(
                id = accident.id,
                updated = accident.updated,
                created = accident.created,
                resolved = accident.resolved,
                verified = accident.verified,
                hidden = accident.hidden,
                description = accident.description,
                conflict = accident.conflict,
                messages = accident.messages,
                location = accident.location,
                type = accident.type,
                hardness = accident.hardness
            )
        }
    }
}