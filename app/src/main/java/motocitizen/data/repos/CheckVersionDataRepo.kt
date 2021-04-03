package motocitizen.data.repos

import io.reactivex.Single
import motocitizen.data.network.version.CheckVersionApi
import motocitizen.data.network.version.VersionStatus
import motocitizen.domain.repos.CheckVersionRepo
import motocitizen.domain.utils.schedulersIoToMain
import javax.inject.Inject

class CheckVersionDataRepo @Inject constructor(
    private val api: CheckVersionApi
) : CheckVersionRepo {

    override fun checkVersion(version: String): Single<VersionStatus> {
        return api.checkVersion(version)
            .map { it.status }
            .schedulersIoToMain()
    }
}