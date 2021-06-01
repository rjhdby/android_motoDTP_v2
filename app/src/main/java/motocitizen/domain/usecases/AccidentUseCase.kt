package motocitizen.domain.usecases

import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address
import motocitizen.domain.repos.AccidentRepo
import javax.inject.Inject

class AccidentUseCase @Inject constructor(
    private val accidentRepo: AccidentRepo,
) {
    fun getAccidentList(
        depth: Int,
        lat: Double? = null,
        lon: Double? = null,
        radius: Int? = null,
        userId: String,
        lastFetch: Int? = null,
    ) = accidentRepo.getAccidentList(
        depth = depth,
        lat = lat,
        lon = lon,
        radius = radius,
        lastFetch = lastFetch,
        userId = userId,
    )

    fun createAccident(
        type: AccidentType,
        hardness: AccidentHardness?,
        location: Address,
        description: String,
    ) = accidentRepo.createAccident(
        type = type,
        hardness = hardness,
        location = location,
        description = description,
    )

    fun getAccident(userId: String, id: String) = accidentRepo.getAccident(userId, id)

    fun setConflict(userId: String, id: String) = accidentRepo.setConflict(userId, id)

    fun dropConflict(userId: String, id: String) = accidentRepo.dropConflict(userId, id)
}