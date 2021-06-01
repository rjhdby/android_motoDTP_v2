package motocitizen.domain.repos

import io.reactivex.Single
import okhttp3.ResponseBody

interface NominationRepo {
    fun getNomination(lat: Double, lon: Double): Single<ResponseBody>
}