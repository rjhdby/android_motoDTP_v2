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
        lastFetch: Int? = null,
    ) = accidentRepo.getAccidentList(
        depth = depth,
        lat = lat,
        lon = lon,
        radius = radius,
        lastFetch = lastFetch,
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

    fun getAccident(id: String) = accidentRepo.getAccident(id)

    fun setConflict(id: String) = accidentRepo.setConflict(id)

    fun dropConflict(id: String) = accidentRepo.dropConflict(id)
}