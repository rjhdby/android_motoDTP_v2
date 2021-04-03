package motocitizen.data.network.user

import motocitizen.domain.exceptions.AuthException
import motocitizen.domain.providers.UserDataProvider
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val userDataProvider: UserDataProvider) : Interceptor {

    companion object {
        const val AUTH_HEADER_TITLE = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = userDataProvider.getAuthToken() ?: getNewAuthToken() ?: throw AuthException()
        requestBuilder.addHeader(AUTH_HEADER_TITLE, token)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun getNewAuthToken(): String? {
//        try {
//            userDataProvider.getUserData()
//        } catch (th: Throwable) {
//            throw AuthException()
//        }
        return userDataProvider.getAuthToken()
    }
}