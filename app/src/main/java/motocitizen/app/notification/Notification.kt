package motocitizen.app.notification

import android.app.PendingIntent

data class Notification(
    val id: Int,
    val title: String,
    val text: String,
    val pendingIntent: PendingIntent? = null
)