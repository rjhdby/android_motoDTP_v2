package motocitizen.di.module.application

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import motocitizen.app.device.DeviceManager
import motocitizen.app.notification.NotificationProvider
import motocitizen.app.push.FirebasePushManager
import motocitizen.data.repos.AppDataRepo
import motocitizen.data.storage.ObjectDataTransformer
import motocitizen.data.storage.SessionMemory
import motocitizen.data.storage.keyvalue.SharedPrefsStorage
import motocitizen.data.storage.keyvalue.SharedPrefsStorageImpl
import motocitizen.data.storage.user.UserMemoryStorage
import motocitizen.data.storage.user.UserStorage
import motocitizen.domain.repos.AppRepo
import motocitizen.domain.storage.ObjectTransformer
import motocitizen.domain.usecases.PushUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideObjectTransformer(): ObjectTransformer = ObjectDataTransformer()

    @Provides
    @Singleton
    fun provideApplicationRepo(
        sharedPrefsStorage: SharedPrefsStorage,
        objectTransformer: ObjectTransformer,
    ): AppRepo =
        AppDataRepo(
            sharedPrefsStorage,
            objectTransformer
        )

    @Provides
    @Singleton
    fun provideFusedLocationClient(@ApplicationContext appContext: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(appContext)
    }


    @Provides
    @Singleton
    fun provideSharedPrefsStorage(@ApplicationContext appContext: Context): SharedPrefsStorage {
        return SharedPrefsStorageImpl(
            appContext
        )
    }

    @Provides
    @Singleton
    fun providePushManager(): FirebasePushManager {
        return FirebasePushManager()
    }

    @Provides
    @Singleton
    fun provideDeviceManager(@ApplicationContext appContext: Context): DeviceManager {
        return DeviceManager(appContext)
    }

    @Provides
    @Singleton
    fun provideNotificationProvider(@ApplicationContext appContext: Context): NotificationProvider {
        return NotificationProvider(
            appContext
        )
    }

    @Provides
    @Singleton
    fun providePushUseCase(
        appRepo: AppRepo,
    ): PushUseCase {
        return PushUseCase(appRepo)
    }

    @Provides
    @Singleton
    fun provideUserStorage(sessionMemory: SessionMemory): UserStorage =
        UserMemoryStorage(sessionMemory)
}