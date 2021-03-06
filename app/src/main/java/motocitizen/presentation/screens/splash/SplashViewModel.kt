package motocitizen.presentation.screens.splash

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import motocitizen.presentation.base.viewmodel.BaseViewModel
import timber.log.Timber
import java.net.InetAddress
import javax.inject.Inject

const val VPN_HOST = "ya.ru"

@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel() {

    enum class ErrorType {
        NO_INTERNET
    }

    val checkState = MutableLiveData<Boolean>()
    val errorDesc = MutableLiveData<ErrorType>()

    fun onAfterInit() {}

    fun work() {
        val thread = Thread {
            try {
                val vpnIp: String?
                try { //Если инет есть, имя разрезолвится в IP. Иначе: exception "Unable to resolve host: No address associated with hostname"
                    val inetAddress: InetAddress = InetAddress.getByName(VPN_HOST)
                    vpnIp = inetAddress.hostAddress
                } catch (e: Exception) {
                    Timber.e("inetAddress exception: %s", e.localizedMessage)
                    errorDesc.postValue(ErrorType.NO_INTERNET)
                    return@Thread
                }
                checkState.postValue(true)
            } catch (e: java.lang.Exception) {
                Timber.e("Thread exception: %s", e.localizedMessage)
            }
        }
        thread.start()
    }
}