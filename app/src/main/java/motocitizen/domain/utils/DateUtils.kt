package motocitizen.domain.utils

import java.util.*

const val MS_IN_MINUTE = 60_000
private const val NOW = "Только что"
private const val TIME_FORMAT = "%dч %dм"

fun Date.getIntervalFromNowInText(): String {
    val minutes = ((Date().time - time) / MS_IN_MINUTE).toInt()
    if (minutes == 0) {
        return NOW
    }
    return String.format(TIME_FORMAT, minutes / 60, minutes % 60)
}