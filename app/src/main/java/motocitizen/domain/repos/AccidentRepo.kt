package motocitizen.domain.repos

import io.reactivex.Single
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address

interface AccidentRepo {
    fun getAccidentList(
        depth: Int,
        lat: Double?,
        lon: Double?,
        radius: Int?,
        lastFetch: Int?
    ): Single<List<Accident>>

    fun createAccident(
        type: AccidentType,
        hardness: AccidentHardness,
        location: Address,
        description: String,
    ): Single<Accident>

    fun getAccident(id: String): Single<Accident>
}