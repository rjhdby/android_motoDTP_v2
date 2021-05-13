package motocitizen.data.network.main

import motocitizen.data.network.factory.ApiFactory
import motocitizen.data.network.factory.HttpClientFactory
import motocitizen.data.network.factory.ServerConfig
import motocitizen.domain.providers.UserDataProvider
import javax.inject.Inject
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager

class UserApiFactory @Inject constructor(
    private val userDataProvider: UserDataProvider,
    trustManager: TrustManager,
    sslSocketFactory: SSLSocketFactory,
) : ApiFactory(
    ServerConfig.DEFAULT,
    HttpClientFactory.okHttpClient({
        addInterceptor(
            AuthInterceptor(
                userDataProvider
            )
        )
    },
        trustManager = trustManager,
        sslSocketFactory = sslSocketFactory
    )
)