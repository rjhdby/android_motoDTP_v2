package motocitizen.domain.usecases

import motocitizen.domain.repos.AccidentRepo
import javax.inject.Inject

class AccidentUseCase @Inject constructor(
    private val accidentRepo: AccidentRepo,
) {
    fun getAccidentList(
        token: String,
        depth: Int,
        lat: Double? = null,
        lon: Double? = null,
        radius: Int? = null,
        lastFetch: Int? = null,
    ) = accidentRepo.getAccidentList(
        token = token,
        depth = depth,
        lat = lat,
        lon = lon,
        radius = radius,
        lastFetch = lastFetch,
    )
}