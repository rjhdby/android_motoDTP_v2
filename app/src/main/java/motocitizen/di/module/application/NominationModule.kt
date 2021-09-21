package motocitizen.di.module.application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import motocitizen.data.network.main.UserApiFactory
import motocitizen.data.network.nomination.NominationApi
import motocitizen.data.repos.NominationDataRepo
import motocitizen.domain.repos.NominationRepo
import motocitizen.domain.usecases.NominationUseCase

@Module
@InstallIn(ViewModelComponent::class)
class NominationModule {

    @Provides
    fun provideNominationRepo(
        api: NominationApi,
    ): NominationRepo = NominationDataRepo(api)

    @Provides
    fun provideNominationApi(factory: UserApiFactory) = factory.create(NominationApi::class.java)

    @Provides
    fun provideAccidentUseCase(repository: NominationRepo) =
        NominationUseCase(repository)
}