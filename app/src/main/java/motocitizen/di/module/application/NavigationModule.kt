package motocitizen.di.module.application

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import motocitizen.main.R

@Module
@InstallIn(ViewModelComponent::class)
object NavigationModule {

    @Provides
    fun provideNavController(activity: Activity): NavController {
        return activity.findNavController(R.id.nav_host_container)
    }
}