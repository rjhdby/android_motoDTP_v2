package motocitizen.data.repos

import io.reactivex.Single
import motocitizen.data.converters.AccidentConverter
import motocitizen.data.network.accident.AccidentApi
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.model.accident.AccidentHardness
import motocitizen.domain.model.accident.AccidentType
import motocitizen.domain.model.accident.Address
import motocitizen.domain.repos.AccidentRepo
import motocitizen.domain.utils.schedulersIoToMain
import okhttp3.ResponseBody
import javax.inject.Inject

class AccidentDataRepo @Inject constructor(
    private val api: AccidentApi,
) : AccidentRepo {
    override fun getAccidentList(
        depth: Int,
        lat: Double?,
        lon: Double?,
        radius: Int?,
        userId: String,
        lastFetch: Int?
    ): Single<List<Accident>> {
        return api.getAccidentList(depth, lat, lon, radius, lastFetch)
            .map { AccidentConverter.toAccidentList(userId, it) }
            .schedulersIoToMain()
    }

    override fun createAccident(
        type: AccidentType,
        hardness: AccidentHardness?,
        location: Address,
        description: String
    ): Single<ResponseBody> {
        return api.createAccident(
            AccidentConverter.toCreateAccidentRequest(
                type,
                hardness,
                location,
                description
            )
        )
            .schedulersIoToMain()
    }

    override fun getAccident(userId: String, id: String): Single<Accident> {
        return api.getAccident(id)
            .map { AccidentConverter.toAccident(userId, it) }
            .schedulersIoToMain()
    }

    override fun setConflict(userId: String, id: String): Single<Accident> {
        return api.setConflict(id)
            .map { AccidentConverter.toAccident(userId, it) }
            .schedulersIoToMain()
    }

    override fun dropConflict(userId: String, id: String): Single<Accident> {
        return api.dropConflict(id)
            .map { AccidentConverter.toAccident(userId, it) }
            .schedulersIoToMain()
    }
}