package motocitizen.domain.model.accident

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.google.type.LatLng
import motocitizen.domain.utils.distanceString
import motocitizen.domain.utils.getIntervalFromNowInText
import java.text.SimpleDateFormat
import java.util.*

data class Accident(
    val id: String,// Идентификатор аварии
    val created: String,// Идентификатор аварии
    var type: AccidentType,
    var resolved: String? = null,
    var verified: Boolean,
    var hidden: Boolean,
//        @field:JsonSerialize(using = ToStringSerializer::class) val creator: ObjectId,
    var location: Address,
    var description: String,
    var conflict: Boolean = false,
    var messages: Int,
    var hardness: AccidentHardness
) {
    companion object {
        const val MESSAGE_FORMAT = "<b>%s</b>"
        const val TITLE_FORMAT = "%s%s(%s)%n%s%n%s"
        const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ"
    }

    var coordinates: LatLng = LatLng.newBuilder()
        .setLongitude(location.lon)
        .setLatitude(location.lat)
        .build()

    //TODO реализовать логику
    fun isOwner(): Boolean {
        return true
    }

    fun messagesText(): Spanned {
        val text = formatMessagesText(this)
        return if (Build.VERSION.SDK_INT >= 24) {
            Html.fromHtml(text, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(text)
        }
    }

    //todo remove html
    fun formatMessagesText(accident: Accident): String {
        return if (accident.messages == 0) ""
//                val read = StoreMessages.getLast(accident.id)
//                return if (accident.messages > read)
//                    String.format("<font color=#C62828><b>(%s)</b></font>", accident.messagesCount)
        else {

            String.format(MESSAGE_FORMAT, accident.messages)
        }
    }

    fun isAccident(): Boolean =
        type in arrayOf(
            AccidentType.MOTO_AUTO,
            AccidentType.MOTO_MOTO,
            AccidentType.MOTO_PEDESTRIAN,
            AccidentType.SOLO
        )

    fun title(): String {
        val damage =
            if (hardness == AccidentHardness.UNKNOWN || !isAccident()) "" else ", " + hardness.text
        return String.format(
            TITLE_FORMAT,
            type.text,
            damage,
            distanceString(),
            location.address,
            description
        )
    }

    fun getTimeAfterCreation(): String {
        val sdf = SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
        return sdf.parse(created)!!.getIntervalFromNowInText()
    }
}