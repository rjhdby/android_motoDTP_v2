package motocitizen.app

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import motocitizen.data.network.user.User
import motocitizen.main.BuildConfig
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class App : Application() {

    companion object {
        const val SEARCH_DELAY_MILLISECONDS = 1000L
        var isLocPermission = false
        var authToken: String? = null
        lateinit var user: User
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
        JodaTimeAndroid.init(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(object : DebugTree() {
                override fun isLoggable(priority: Int): Boolean {
                    return priority >= Log.INFO
                }
            })
        }
    }
}