package motocitizen.data.network.main

import motocitizen.data.network.factory.ApiFactory
import motocitizen.data.network.factory.HttpClientFactory
import motocitizen.data.network.factory.ServerConfig
import javax.inject.Inject
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager

class UserApiFactory @Inject constructor(
    trustManager: TrustManager,
    sslSocketFactory: SSLSocketFactory,
) : ApiFactory(
    ServerConfig.DEFAULT,
    HttpClientFactory.okHttpClient({
    },
        trustManager = trustManager,
        sslSocketFactory = sslSocketFactory
    )
)