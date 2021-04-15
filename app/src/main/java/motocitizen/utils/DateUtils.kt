package motocitizen.utils

import java.text.SimpleDateFormat
import java.util.*

const val MS_IN_HOUR = 3_600_000
const val MS_IN_MINUTE = 60_000

fun Date.timeString(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)

fun Date.dateTimeString(): String = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault()).format(this)

fun Date.getIntervalFromNowInText(): String {
    val minutes = ((Date().time - time) / MS_IN_MINUTE).toInt()
    if (minutes == 0) return "Только что"
    return String.format("%dч %dм", minutes / 60, minutes % 60)
}

fun Date.seconds() = time / 1000
