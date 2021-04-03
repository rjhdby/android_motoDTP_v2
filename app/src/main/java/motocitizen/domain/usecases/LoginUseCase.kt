package motocitizen.domain.usecases

import io.reactivex.Completable
import motocitizen.app.device.DeviceManager
import motocitizen.app.push.FirebasePushManager
import motocitizen.domain.model.login.SignIn
import motocitizen.domain.repos.LoginRepo
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepo: LoginRepo,
    private val pushManager: FirebasePushManager,
    private val deviceManager: DeviceManager,
) {

    fun signIn(): Completable {
        return pushManager.requestPushId()
            .flatMapCompletable { token ->
                loginRepo.login(
                    SignIn(
                        token = token,
                        installId = deviceManager.getDeviceId()
                    )
                )
            }
    }
}