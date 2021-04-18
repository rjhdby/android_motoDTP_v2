@file:JvmName("DateUtils")

package motocitizen.domain.utils

import java.util.*

const val MS_IN_MINUTE = 60_000


fun Date.getIntervalFromNowInText(): String {
    val minutes = ((Date().time - time) / MS_IN_MINUTE).toInt()
    if (minutes == 0) return "Только что"
    return String.format("%dч %dм", minutes / 60, minutes % 60)
}


