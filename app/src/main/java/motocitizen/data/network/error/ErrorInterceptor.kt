package motocitizen.data.network.error

import android.util.Log
import motocitizen.domain.exceptions.NetworkException
import motocitizen.domain.exceptions.ServerException
import motocitizen.domain.exceptions.TimeoutException
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = try {
            chain.proceed(chain.request())
        } catch (th: Throwable) {
            Log.e("OkHttp Exception", th.localizedMessage)
            when (th) {
                is SocketTimeoutException -> throw TimeoutException()
                is IOException -> throw NetworkException()
                else -> throw th
            }
        }
        if (response.isSuccessful)
            return response

        val responseBody: String? = response.body?.string()
        try {
            Log.e("ErrorInterceptor", "Server response: $responseBody")
            val jsonResponse = JSONObject(responseBody)
            if (jsonResponse.has("result")) {
                val result = jsonResponse.getJSONObject("result")
                val message: String = result.getString("message")
                val details = if (result.getString("details").equals("null")) {
                    ""
                } else {
                    result.getString("details")
                }
                val exception =
                    ServerException(
                        response.code,
                        message,
                        details
                    )
                throw ServerErrorMapper.map(
                    exception
                )
            } else {
                //выбрасываем ошибку со статусом запроса- для случаев,
                // когда ответ сервера не содержит тега result(как при истекщей сессии)
                //при этом ориентируясь на httpCode
                throw ServerErrorMapper.map(
                    ServerException(
                        response.code,
                        "",
                        ""
                    )
                )
            }
        } catch (ex: JSONException) { // Например waf может прислать 403
            val exception =
                ServerException(
                    response.code,
                    responseBody ?: "",
                    ""
                )
            throw ServerErrorMapper.map(
                exception
            )
        }
    }
}