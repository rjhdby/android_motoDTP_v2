package motocitizen.domain.usecases

import motocitizen.domain.repos.AccidentRepo
import javax.inject.Inject

class AccidentUseCase @Inject constructor(
        private val accidentRepo: AccidentRepo,
) {
}