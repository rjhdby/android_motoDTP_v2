package motocitizen.data.converters

import motocitizen.data.network.accident.AccidentResponse
import motocitizen.data.network.accident.CreateAccidentRequest
import motocitizen.data.utils.getNotNull
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address

object AccidentConverter {
    fun toAccidentList(userId: String, response: List<AccidentResponse>): List<Accident> {
        val result = getNotNull(response)
        return result.map { accident ->
            Accident(
                id = accident.id,
                created = accident.created,
                type = accident.type,
                resolved = accident.resolved,
                verified = accident.verified,
                hidden = accident.hidden,
                hardness = accident.hardness,
                creator = accident.creator,
                creatorNick = accident.creatorNick,
                location = accident.location,
                description = accident.description,
                conflict = accident.conflict,
                isOwner = accident.creator == userId,
                messages = accident.messages
            )
        }
    }

    fun toAccident(userId: String, response: AccidentResponse): Accident {
        val result = getNotNull(response)
        return result.let {
            Accident(
                id = it.id,
                created = it.created,
                type = it.type,
                resolved = it.resolved,
                verified = it.verified,
                hidden = it.hidden,
                hardness = it.hardness,
                creator = it.creator,
                creatorNick = it.creatorNick,
                location = it.location,
                description = it.description,
                conflict = it.conflict,
                isOwner = it.creator == userId,
                messages = it.messages,
            )
        }
    }

    fun toCreateAccidentRequest(
        type: AccidentType,
        hardness: AccidentHardness?,
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