package motocitizen.domain.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

private val ISO_DATE_TIME_FORMATTER: DateTimeFormatter =
    DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

private val FULL_DATE_TIME_FORMATTER: DateTimeFormatter =
    DateTimeFormat.forPattern("HH:mm:ss, dd MMMM YYYY")

private val EVENT_DATE_FORMATTER: DateTimeFormatter =
    DateTimeFormat.forPattern("yyyy-MM-dd")

private val TIME_ONLY_FORMATTER: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm")

private val DATE_PREDICT_FORMATTER: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm dd MMMM")

fun getOffset(): Int {
    val tz = DateTimeZone.getDefault()
    val instant = DateTime.now().millis
    return tz.getOffset(instant)
}

