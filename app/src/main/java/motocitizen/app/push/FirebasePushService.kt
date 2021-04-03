package motocitizen.app.push

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import motocitizen.app.notification.Notification
import motocitizen.app.notification.NotificationProvider
import motocitizen.domain.model.accident.AccidentPush
import motocitizen.domain.usecases.PushUseCase
import motocitizen.main.R
import motocitizen.presentation.screens.root.RootActivity
import javax.inject.Inject
import kotlin.random.Random

const val NOTIFICATION_ACCIDENT_ID_KEY = "NOTIFICATION_ACCIDENT_ID_KEY"
const val NOTIFICATION_ACCIDENT_NAME_KEY = "NOTIFICATION_ACCIDENT_NAME_KEY"
private const val ID = "id"
private const val DESCRIPTION = "description"
private const val HEADER = "header"
private const val NAME = "name"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
@AndroidEntryPoint
class FirebasePushService : FirebaseMessagingService(), LifecycleObserver {

    private var isAppInForeground = false

    @Inject
    lateinit var notificationProvider: NotificationProvider

    @Inject
    lateinit var pushUseCase: PushUseCase

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }

    override fun onMessageReceived(push: RemoteMessage) {
        handlePush(push)
    }

    private fun handlePush(push: RemoteMessage) {
        val payload = push.data
        val accidentId = payload[ID] ?: return
        val accidentName = payload[NAME] ?: return
        val description = payload[DESCRIPTION] ?: return
        val header = payload[HEADER] ?: return
        val accidentPush = AccidentPush(
            pushId = Random.nextInt(),
            accidentId = accidentId,
            accidentName = accidentName,
            description = description,
            header = header
        )
        pushUseCase.saveAccidentPush(accidentPush)
        postNotification(
            payload = push.data,
            id = accidentPush.pushId,
            title = header,
            body = description
        )
    }

    private fun postNotification(
        payload: FirebasePushPayload,
        id: Int = Random.nextInt(),
        title: String,
        body: String,
    ) {
        notificationProvider.notify(
            Notification(
                id = id,
                title = title,
                text = body,
                pendingIntent = getPendingIntent(payload)
            )
        )
    }

    private fun getPendingIntent(payload: FirebasePushPayload): PendingIntent? {
        val accidentId = payload[ID] ?: return null
        val accidentName = payload[NAME] ?: return null
        return if (isAppInForeground) {
            getForegroundPendingIntent(accidentId, accidentName)
        } else {
            getBackgroundPendingIntent(accidentId, accidentName)
        }
    }

    private fun getBackgroundPendingIntent(
        accidentId: String,
        accidentName: String,
    ): PendingIntent {
        return NavDeepLinkBuilder(this)
            .setComponentName(RootActivity::class.java)
            .setGraph(R.navigation.home)
            //.setDestination(R.id.accidentFragment)
            //.setArguments(AccidentFragmentArgs(accidentId, accidentName).toBundle())
            .createPendingIntent()
    }

    private fun getForegroundPendingIntent(
        accidentId: String,
        accidentName: String,
    ): PendingIntent {
        val intent = Intent(this, RootActivity::class.java).apply {
            putExtra(NOTIFICATION_ACCIDENT_ID_KEY, accidentId)
            putExtra(NOTIFICATION_ACCIDENT_NAME_KEY, accidentName)
        }
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}