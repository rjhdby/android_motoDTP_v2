package motocitizen.domain.usecases

import motocitizen.domain.repos.WebRepo
import javax.inject.Inject

class WebUseCase @Inject constructor(
    private val webRepo: WebRepo
) {
}