package motocitizen.data.network.accident

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET

interface AccidentApi {
    @GET("/accident/list/")
    fun getAccidentList(@Body request: AccidentRequest): Observable<List<AccidentResponse>>
}