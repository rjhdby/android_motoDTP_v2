package motocitizen.domain.usecases

import io.reactivex.Single
import motocitizen.data.network.version.VersionStatus
import motocitizen.domain.repos.CheckVersionRepo
import motocitizen.main.BuildConfig
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(
    private val repo: CheckVersionRepo
) {
    operator fun invoke(): Single<VersionStatus> =
        repo.checkVersion(BuildConfig.API_VERSION)
}