package motocitizen.di.module.application

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import motocitizen.data.network.factory.CustomKeyManager
import motocitizen.data.storage.keyvalue.SharedPrefsStorage
import java.security.KeyStore
import java.security.SecureRandom
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory

@Module
@InstallIn(SingletonComponent::class)
object SslModule {

    @Provides
    @Singleton
    fun provideCustomTrustManager(): TrustManager {
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        val trustManagers = trustManagerFactory.trustManagers
        return trustManagers[0]
    }

    @Provides
    @Singleton
    fun provideCustomKeyManager(
        @ApplicationContext context: Context,
        sharedPrefsStorage: SharedPrefsStorage,
    ): CustomKeyManager = CustomKeyManager(
        applicationContext = context,
        sharedPreferences = sharedPrefsStorage
    )

    @Provides
    @Singleton
    fun provideSslSocketFactory(
        customKeyManager: CustomKeyManager,
        trustManager: TrustManager,
    ): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("TLSv1.2")
        sslContext.init(
            arrayOf(customKeyManager),
            arrayOf(trustManager),
            SecureRandom())
        return sslContext.socketFactory
    }
}