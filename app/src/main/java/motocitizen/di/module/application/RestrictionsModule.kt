package motocitizen.di.module.application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import motocitizen.data.network.restrictions.RestrictionsApi
import motocitizen.data.network.user.UserApiFactory
import motocitizen.data.repos.RestrictionsDataRepo
import motocitizen.data.storage.restrictions.RestrictionsStorage
import motocitizen.domain.repos.RestrictionsRepo
import motocitizen.domain.usecases.GetRestrictionsUseCase

@Module
@InstallIn(ActivityComponent::class)
object RestrictionsModule {

    @Provides
    fun provideCheckVersionApi(factory: UserApiFactory) =
        factory.create(RestrictionsApi::class.java)

    @Provides
    fun provideRestrictionsRepo(
        api: RestrictionsApi,
        restrictionsStorage: RestrictionsStorage,
    ): RestrictionsRepo =
        RestrictionsDataRepo(api, restrictionsStorage)

    @Provides
    fun provideGetRestrictionsUseCase(
        repo: RestrictionsRepo,
    ): GetRestrictionsUseCase = GetRestrictionsUseCase(repo)
}