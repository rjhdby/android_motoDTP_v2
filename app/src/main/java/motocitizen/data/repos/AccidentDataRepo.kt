package motocitizen.data.repos

import io.reactivex.Observable
import motocitizen.data.converters.AccidentConverter
import motocitizen.data.network.accident.AccidentApi
import motocitizen.domain.model.accident.Accident
import motocitizen.domain.repos.AccidentRepo
import motocitizen.domain.utils.schedulersIoToMain
import javax.inject.Inject

class AccidentDataRepo @Inject constructor(
        private val api: AccidentApi,
) : AccidentRepo {
    override fun getAccidentList(token: String, depth: Int, lat: Double?, lon: Double?, radius: Int?, lastFetch: Int?): Observable<List<Accident>> {
        return api.getAccidentList(AccidentConverter.toAccidentRequest(token, depth, lat, lon, radius, lastFetch))
                .map { AccidentConverter.toAccidentList(it) }
                .schedulersIoToMain()
    }
}