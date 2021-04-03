package motocitizen.data.network.version

import motocitizen.data.network.factory.ApiFactory
import motocitizen.data.network.factory.HttpClientFactory
import motocitizen.data.network.factory.ServerConfig
import motocitizen.main.BuildConfig
import javax.inject.Inject
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager

class CheckVersionApiFactory @Inject constructor(
    trustManager: TrustManager,
    sslSocketFactory: SSLSocketFactory,
) : ApiFactory(
    ServerConfig.Builder(BuildConfig.SERVER_URL).build(),
    HttpClientFactory.okHttpClient(
        trustManager = trustManager,
        sslSocketFactory = sslSocketFactory
    )
)