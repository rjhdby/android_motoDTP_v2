package motocitizen.domain.repos

import io.reactivex.Single
import motocitizen.domain.model.accident.Accident

interface AccidentRepo {
    fun getAccidentList(
            token: String,
            depth: Int,
            lat: Double?,
            lon: Double?,
            radius: Int?,
            lastFetch: Int?
    ): Single<List<Accident>>
}