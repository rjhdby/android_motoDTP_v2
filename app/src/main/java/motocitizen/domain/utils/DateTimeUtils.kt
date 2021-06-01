package motocitizen.domain.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

private const val TIME_FORMAT = "HH:mm"

fun DateTime.fromUts(): DateTime = this.plusMillis(getOffset())

fun DateTime.asTime(): String = this.toString(TIME_FORMAT)

private val DATE_TIME_FORMATTER: DateTimeFormatter =
    DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")

fun getOffset(): Int {
    val tz = DateTimeZone.getDefault()
    val instant = DateTime.now().millis
    return tz.getOffset(instant)
}

@Throws(IllegalArgumentException::class)
fun String.formatAsISODateTime(): DateTime {
    return DATE_TIME_FORMATTER.parseDateTime(this)
}

