package motocitizen.di.module.application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import motocitizen.app.device.DeviceManager
import motocitizen.app.push.FirebasePushManager
import motocitizen.data.network.login.LoginApi
import motocitizen.data.network.user.UserApiFactory
import motocitizen.data.repos.LoginDataRepo
import motocitizen.data.storage.keyvalue.SharedPrefsStorage
import motocitizen.domain.repos.LoginRepo
import motocitizen.domain.storage.ObjectTransformer
import motocitizen.domain.usecases.LoginUseCase

@Module
@InstallIn(ActivityComponent::class)
object LoginModule {

    @Provides
    fun provideLoginRepository(
        loginApi: LoginApi,
        sharedPrefsStorage: SharedPrefsStorage,
        objectTransformer: ObjectTransformer
    ): LoginRepo =
        LoginDataRepo(
            loginApi,
            sharedPrefsStorage,
            objectTransformer
        )

    @Provides
    fun provideLoginApi(factory: UserApiFactory) = factory.create(
        LoginApi::class.java)

    @Provides
    fun provideLoginUseCase(
        loginRepo: LoginRepo,
        pushManager: FirebasePushManager,
        deviceManager: DeviceManager
    ): LoginUseCase {
        return LoginUseCase(
            loginRepo,
            pushManager,
            deviceManager
        )
    }
}