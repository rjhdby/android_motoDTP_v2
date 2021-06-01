package motocitizen.data.repos

import io.reactivex.Single
import motocitizen.data.network.nomination.NominationApi
import motocitizen.domain.repos.NominationRepo
import motocitizen.domain.utils.schedulersIoToMain
import okhttp3.ResponseBody
import javax.inject.Inject

class NominationDataRepo @Inject constructor(
    private val api: NominationApi,
) : NominationRepo {
    override fun getNomination(lat: Double, lon: Double): Single<ResponseBody> {
        return api.getNomination(lat, lon)
            .schedulersIoToMain()
    }
}