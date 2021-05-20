package motocitizen.di.module.application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import motocitizen.data.network.main.UserApiFactory
import motocitizen.data.network.message.MessageApi
import motocitizen.data.repos.MessageDataRepo
import motocitizen.domain.repos.MessageRepo
import motocitizen.domain.usecases.MessageUseCase

@Module
@InstallIn(ActivityComponent::class)
object MessageModule {

    @Provides
    fun provideMessageRepo(
        api: MessageApi,
    ): MessageRepo = MessageDataRepo(api)

    @Provides
    fun provideMessageApi(factory: UserApiFactory) = factory.create(MessageApi::class.java)

    @Provides
    fun provideAccidentUseCase(repository: MessageRepo) =
        MessageUseCase(repository)
}