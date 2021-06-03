package motocitizen.di.module.application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import motocitizen.data.network.main.UserApiFactory
import motocitizen.data.network.user.UserApi
import motocitizen.data.repos.UserDataRepo
import motocitizen.data.storage.user.UserStorage
import motocitizen.domain.repos.UserRepo
import motocitizen.domain.usecases.GetUserUseCase

@Module
@InstallIn(ActivityComponent::class)
object UserModule {

    @Provides
    fun provideCheckVersionApi(factory: UserApiFactory) =
        factory.create(UserApi::class.java)

    @Provides
    fun provideUserRepo(
        api: UserApi,
        userStorage: UserStorage,
    ): UserRepo =
        UserDataRepo(api, userStorage)

    @Provides
    fun provideGetUserUseCase(
        repo: UserRepo,
    ): GetUserUseCase = GetUserUseCase(repo)
}