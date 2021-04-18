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
    var updated: String,
    var type: Type,
    var resolved: String? = null,
    var verified: Boolean,
    var hidden: Boolean,
//    var hardness: AccidentHardness,
//        @field:JsonSerialize(using = ToStringSerializer::class) val creator: ObjectId,
    var location: Address,

    var description: String,
    var conflict: Boolean = false,
    var messages: Int,
    var hardness: Hardness

) {
    var coordinates: LatLng = LatLng.newBuilder()
        .setLongitude(location.lon)
        .setLatitude(location.lat)
        .build()

    //TODO
    companion object {
        var isOwner = true
    }

    fun isOwner(): Boolean {
        isOwner = !isOwner
        return isOwner
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
        else
            String.format("<b>%s</b>", accident.messages)
    }

    fun isAccident(): Boolean =
        type in arrayOf(Type.MOTO_AUTO, Type.MOTO_MOTO, Type.MOTO_MAN, Type.SOLO)

    fun title(): String {
        val damage =
            if (hardness == Hardness.UNKNOWN || !isAccident()) "" else ", " + hardness.text
        return String.format(
            "%s%s(%s)%n%s%n%s",
            type.text,
            damage,
            distanceString(),
            location.address,
            description
        )
    }

    fun getTimeAfterCreation(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ", Locale.getDefault())
        return sdf.parse(created)!!.getIntervalFromNowInText()
    }


}