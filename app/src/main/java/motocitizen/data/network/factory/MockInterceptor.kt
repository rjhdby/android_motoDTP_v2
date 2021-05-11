package motocitizen.data.network.factory

import motocitizen.data.network.factory.mocks.*
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.io.IOException

/**
 * This will help us to test our networking code while a particular API is not implemented
 * yet on Backend side.
 */
class MockInterceptor : Interceptor {

    private fun bodyToString(request: Request): String? {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        val request = chain.request()
        val responseString =
            if (request.body != null) { // Note: Для POST
                val body = bodyToString(request)
                when {
                    uri.endsWith("accident/create/") -> {
                        when {
                            // todo Код от проекта - донора
                            //body.equals("{\"categories\":[1,2,3],\"limit\":10,\"page\":1,\"systemID\":\"1\"}") -> getAccidentList
                            else -> accident
                        }
                    }
                    // todo Код от проекта - донора
                    uri.endsWith("/pushToken/update") -> {
                        pushTokenUpdate
                    }
                    else -> empty
                }
            } else { // Note: Для GET
                when {
                    uri.contains("accident/list/") -> getAccidentList
                    uri.endsWith("accident/?id=1") -> accidentFirst
                    uri.endsWith("accident/?id=2") -> accidentSecond
                    uri.endsWith("/checkVersion?version=1.0") -> getCheckVersionNormal
                    uri.endsWith("/checkVersion?version=1.2") -> getCheckVersionNormalDeprecated
                    uri.endsWith("/checkVersion?version=1.3") -> getCheckVersionNormalUnsupported
                    uri.endsWith("/restrictions") -> getRestrictions
                    else -> empty
                }
            }
        return chain.proceed(chain.request())
            .newBuilder()
            //.code(SUCCESS_CODE)
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                responseString.toByteArray()
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}
