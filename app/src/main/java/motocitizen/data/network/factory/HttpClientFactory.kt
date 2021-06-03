package motocitizen.data.network.factory

import motocitizen.app.App
import motocitizen.data.network.error.ErrorInterceptor
import motocitizen.data.network.factory.HttpClientFactory.UnsafeOkHttpClient.Companion.getUnsafeOkHttpClient
import motocitizen.main.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
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
    private const val TOKEN = "token"

    fun okHttpClient(
        builder: OkHttpClient.Builder.() -> Unit = {},
        trustManager: TrustManager,
        sslSocketFactory: SSLSocketFactory,
    ): OkHttpClient {
        //todo #26 Игнорируем самоподписанный сертификат
        //return clientBuilder()
        return getUnsafeOkHttpClient()
            //todo #26 Игнорируем самоподписанный сертификат
            .apply {
                builder(this)
                sslSocketFactory(sslSocketFactory, trustManager as X509TrustManager)
                addInterceptor(getUserAgentInterceptor())
                addInterceptor(getLoggingInterceptor())
                addInterceptor(ErrorInterceptor())
                if (BuildConfig.FLAVOR == "local") {
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
                .header(TOKEN, App.authToken!!)
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
    //todo #26 Игнорируем самоподписанный сертификат
    class UnsafeOkHttpClient {
        companion object {
            fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
                try {
                    // Create a trust manager that does not validate certificate chains
                    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String,
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String,
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    })

                    // Install the all-trusting trust manager
                    val sslContext = SSLContext.getInstance("TLSv1.2")
                    sslContext.init(null, trustAllCerts, SecureRandom())
                    // Create an ssl socket factory with our all-trusting manager
                    val sslSocketFactory = sslContext.socketFactory

                    val builder = OkHttpClient.Builder()
                    builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                    // builder.hostnameVerifier { _, _ -> true }
                    builder.hostnameVerifier(hostnameVerifier = { _, _ -> true })
                    builder.connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    builder.readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    builder.writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    return builder
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }
        }
    }
    //todo Задействовать https #26
}
