package motocitizen.data.network.factory

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class ApiFactory(
    private val serverConfig: ServerConfig,
    private val httpClient: OkHttpClient
) {

    fun <T> create(clazz: Class<T>): T {
        val client = if (serverConfig.pin != null) {
            val sslPinning = CertificatePinner.Builder()
                .add(serverConfig.host, serverConfig.pin)
                //.add(serverConfig.host, serverConfig.pin2)
                .build()
            httpClient.newBuilder().certificatePinner(sslPinning).build()
        } else {
            httpClient
        }
        return Retrofit.Builder()
            .baseUrl(serverConfig.apiUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonFactory.networkGson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(clazz)
    }
}
