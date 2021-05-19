package motocitizen.domain.model.accident

import android.os.Build
import android.os.Parcelable
import android.text.Html
import android.text.Spanned
import com.google.type.LatLng
import kotlinx.android.parcel.Parcelize
import motocitizen.domain.utils.distanceString
import motocitizen.domain.utils.getIntervalFromNowInText
import java.text.SimpleDateFormat
import java.util.*

private const val MESSAGE_FORMAT = "<b>%s</b>"
private const val TITLE_FORMAT = "%s%s(%s)%n%s%n%s"
private const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ"
private const val TIME_FORMAT = "HH:mm"

@Parcelize
data class Accident(
    val id: String,// Идентификатор аварии
    val created: String,
    val type: AccidentType,
    val resolved: String? = null,
    val verified: Boolean,
    val hidden: Boolean,
    val location: Address,
    val description: String,
    val conflict: Boolean = false,
    val messages: Int,
    val hardness: AccidentHardness?
) : Parcelable {

    private var sdf: SimpleDateFormat = SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
    private var time: SimpleDateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())

    var coordinates: LatLng = LatLng.newBuilder()
        .setLongitude(location.lon)
        .setLatitude(location.lat)
        .build()


    val accidentsWithCrashes: EnumSet<AccidentType> = EnumSet.of(
        AccidentType.MOTO_AUTO,
        AccidentType.MOTO_MOTO,
        AccidentType.MOTO_PEDESTRIAN,
        AccidentType.SOLO
    )

    //TODO реализовать логику, является ли текущий юзер создателем инцидента
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
    private fun formatMessagesText(accident: Accident): String {
        return if (accident.messages == 0) ""
//                val read = StoreMessages.getLast(accident.id)
//                return if (accident.messages > read)
//                    String.format("<font color=#C62828><b>(%s)</b></font>", accident.messagesCount)
        else {
            String.format(MESSAGE_FORMAT, accident.messages)
        }
    }

    private fun isAccident(): Boolean {
        return accidentsWithCrashes.contains(type)
    }

    fun title(): String {
        return String.format(
            TITLE_FORMAT,
            type.text,
            if (hardness == null) "" else ", ${hardness.text}",
            distanceString(),
            location.address,
            description
        )
    }

    fun getTimeAfterCreation(): String = sdf.parse(created)!!.getIntervalFromNowInText()

    fun getTimeCreationMS(): Long = sdf.parse(created)!!.time

    fun getTimeCreation(): String = time.format(sdf.parse(created)!!)
}