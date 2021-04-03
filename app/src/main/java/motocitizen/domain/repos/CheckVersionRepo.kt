package motocitizen.domain.repos

import io.reactivex.Single
import motocitizen.data.network.version.VersionStatus

interface CheckVersionRepo {
    fun checkVersion(version: String): Single<VersionStatus>
}