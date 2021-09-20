package motocitizen.domain.repos

import io.reactivex.Single
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address
import okhttp3.ResponseBody

interface AccidentRepo {
    fun getAccidentList(
        depth: Int,
        lat: Double?,
        lon: Double?,
        radius: Int?,
        userId: String,
        lastFetch: Int?
    ): Single<List<Accident>>

    fun createAccident(
        type: AccidentType,
        hardness: AccidentHardness?,
        location: Address,
        description: String,
    ): Single<ResponseBody>

    fun getAccident(userId: String, id: String): Single<Accident>
    fun setConflict(userId: String, id: String): Single<Accident>
    fun dropConflict(userId: String,id: String): Single<Accident>
    fun resolve(userId: String,id: String): Single<Accident>
    fun reopen(userId: String,id: String): Single<Accident>
}