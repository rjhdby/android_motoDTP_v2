package motocitizen.data.network.factory

import motocitizen.data.network.error.ErrorInterceptor
import motocitizen.main.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object HttpClientFactory {

    private const val CONNECT_TIMEOUT_SECONDS = 30L
    private const val READ_TIMEOUT_SECONDS = 30L
    private const val WRITE_TIMEOUT_SECONDS = 30L
    private const val USER_AGENT = "User-Agent"
    private const val ANDROID = "Android"
    private const val APP_VERSION = "App-Version"
    private const val PLATFORM = "Platform"
    private const val PLATFORM_VERSION = "Platform-Version"

    fun okHttpClient(
        builder: OkHttpClient.Builder.() -> Unit = {},
        trustManager: TrustManager,
        sslSocketFactory: SSLSocketFactory,
    ): OkHttpClient {
        return clientBuilder()
            .apply {
                builder(this)
                sslSocketFactory(sslSocketFactory, trustManager as X509TrustManager)
                addInterceptor(getUserAgentInterceptor())
                addInterceptor(getLoggingInterceptor())
                addInterceptor(ErrorInterceptor())
                if (BuildConfig.DEBUG) {
                    addInterceptor(MockInterceptor())
                }
                //addInterceptor(interceptor)
            }
            .build()
    }

    private fun clientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }
    }

    private fun getUserAgentInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithUserAgent = originalRequest.newBuilder()
                .header(USER_AGENT, ANDROID)
                .header(APP_VERSION, BuildConfig.VERSION_NAME)
                .header(PLATFORM, ANDROID)
                .header(PLATFORM_VERSION, android.os.Build.VERSION.RELEASE.toString())
                .build()
            chain.proceed(requestWithUserAgent)
        }
    }

    private fun getLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BODY
//todo: Временно включаем логирование в релизе.
//                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}
