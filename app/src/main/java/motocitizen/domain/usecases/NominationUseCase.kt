package motocitizen.domain.usecases

import motocitizen.domain.repos.NominationRepo
import javax.inject.Inject

class NominationUseCase @Inject constructor(
    private val repo: NominationRepo,
) {
    operator fun invoke(lat: Double, lon: Double) = repo.getNomination(lat, lon)
}