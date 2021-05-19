package motocitizen.app.push

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Single
import motocitizen.domain.exceptions.FirebaseException
import motocitizen.domain.utils.safeOnError
import motocitizen.domain.utils.safeOnSuccess
import motocitizen.domain.utils.schedulersIoToMain
import timber.log.Timber
import javax.inject.Inject

class FirebasePushManager @Inject constructor() {

    fun requestPushId(): Single<String> {
        return Single.create<String> {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        it.safeOnError(FirebaseException())
                        return@OnCompleteListener
                    }

                    val result = task.result
                    if (result == null) {
                        it.safeOnError(FirebaseException())
                        return@OnCompleteListener
                    }

                    it.safeOnSuccess(result.token)
                    Timber.d(result.token)
                })
        }.schedulersIoToMain()
    }
}