package motocitizen.data.utils

import android.location.Address
import android.location.Location
import android.location.LocationManager
import com.google.type.LatLng
import motocitizen.data.gps.LocListener

typealias Meter = Long
typealias Kilometer = Float

const val EQUATOR = 20038

//fun Location.toLatLng(): LatLng = LatLng(latitude, longitude)

fun LatLng.distanceTo(latLng: LatLng): Kilometer = distanceTo(latLng.toLocation())

fun LatLng.distanceTo(location: Location): Kilometer = toLocation().distanceTo(location) / 1000

fun LatLng.toLocation(): Location {
    val location = Location(LocationManager.GPS_PROVIDER)
    location.latitude = latitude
    location.longitude = longitude
    return location
}

fun LatLng.distanceString(): String {
    val meters = metersFromUser()
    return when {
        meters > 1000 -> meters.toKilometers().toString() + "км"
        else -> meters.toString() + "м"
    }
}

fun Meter.toKilometers(): Kilometer = (this / 10).toFloat() / 100

fun LatLng.metersFromUser(): Meter =
    (distanceTo(LocListener.currentLocation.value!!) * 1000).toLong()

fun Address.buildAddressString(): String {
    return StringBuilder()
        .append(locality ?: adminArea ?: getAddressLine(0) ?: "")
        .append(" ")
        .append(thoroughfare ?: subAdminArea ?: "")
        .append(" ")
        .append(featureName ?: "")
        .toString()
        .trim()
}
//val Address.latLng
//    get() = LatLng(latitude, longitude)