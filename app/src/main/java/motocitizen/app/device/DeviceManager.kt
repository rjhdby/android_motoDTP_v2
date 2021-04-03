package motocitizen.app.device

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext

class DeviceManager(@ApplicationContext private val appContext: Context) {

    @SuppressLint("HardwareIds")
    fun getDeviceId(): String = Settings.Secure.getString(appContext.contentResolver, Settings.Secure.ANDROID_ID)

}