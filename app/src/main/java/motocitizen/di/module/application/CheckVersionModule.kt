package motocitizen.di.module.application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import motocitizen.data.network.version.CheckVersionApi
import motocitizen.data.network.version.CheckVersionApiFactory
import motocitizen.data.repos.CheckVersionDataRepo
import motocitizen.domain.repos.CheckVersionRepo
import motocitizen.domain.usecases.CheckVersionUseCase

@Module
@InstallIn(ActivityComponent::class)
object CheckVersionModule {

    @Provides
    fun provideCheckVersionRepository(api: CheckVersionApi): CheckVersionRepo =
        CheckVersionDataRepo(api)

    @Provides
    fun provideCheckVersionApi(factory: CheckVersionApiFactory) =
        factory.create(CheckVersionApi::class.java)

    @Provides
    fun provideCheckVersionUseCase(
        checkVersionRepository: CheckVersionRepo,
    ): CheckVersionUseCase = CheckVersionUseCase(checkVersionRepository)
}