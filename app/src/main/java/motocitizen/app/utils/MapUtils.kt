package motocitizen.app.utils

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import motocitizen.domain.model.accident.Accident
import motocitizen.utils.MS_IN_HOUR
import java.util.*

const val DEFAULT_ZOOM = 16f
private const val ACCIDENT_AGE_NEW = 2
private const val ACCIDENT_AGE_OLD = 6
private const val DENSE = 1f
private const val SEMI_DENSE = 0.5f
private const val TRANSPARENT = 0.2f

fun GoogleMap.accidentMarker(accident: Accident): Marker = addMarker(makeMarker(accident))

private fun calculateAlpha(accident: Accident): Float {
    val age = ((Date().time - accident.getTimeCreationMS()) / MS_IN_HOUR).toInt()
    return when {
        age < ACCIDENT_AGE_NEW -> DENSE
        age < ACCIDENT_AGE_OLD -> SEMI_DENSE
        else -> TRANSPARENT
    }
}

private fun markerTitle(accident: Accident): String {
    val medicine =
        if (accident.hardness == null) "" else ", ${accident.hardness.text}"
    val interval = accident.getTimeAfterCreation()
    return "${accident.type.text}$medicine, $interval назад"
}

private fun makeMarker(accident: Accident) = MarkerOptions()
    .position(LatLng(accident.coordinates.latitude, accident.coordinates.longitude))
    .title(markerTitle(accident))
    .icon(accident.type.icon)
    .alpha(calculateAlpha(accident))