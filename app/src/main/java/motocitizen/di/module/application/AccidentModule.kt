package motocitizen.di.module.application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import motocitizen.data.network.accident.AccidentApi
import motocitizen.data.network.main.UserApiFactory
import motocitizen.data.repos.AccidentDataRepo
import motocitizen.domain.repos.AccidentRepo
import motocitizen.domain.usecases.AccidentUseCase

@Module
@InstallIn(ActivityComponent::class)
object AccidentModule {

    @Provides
    fun provideAccidentRepo(
            api: AccidentApi,
    ): AccidentRepo = AccidentDataRepo(api)

    @Provides
    fun provideAccidentApi(factory: UserApiFactory) = factory.create(AccidentApi::class.java)

    @Provides
    fun provideAccidentUseCase(repository: AccidentRepo) =
            AccidentUseCase(repository)
}