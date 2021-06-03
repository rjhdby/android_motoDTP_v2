package motocitizen.domain.utils

import motocitizen.data.utils.distanceString
import motocitizen.domain.model.accident.Accident

//fun Id.name() = Content.volunteerName(this)
//
//fun Accident.getAccidentTextToCopy(): String {
//    val medicineText = if (medicine == Medicine.UNKNOWN) "" else medicine.text + ". "
//    return "${time.dateTimeString()} ${owner.name()}: ${type.text}.$medicineText $address. $description."
//}

val Accident.latitude
    inline get() = location.lat

val Accident.longitude
    inline get() = location.lon

fun Accident.distanceString(): String = coordinates.distanceString()

//fun Accident.distanceTo(location: Location) = coordinates.distanceTo(location)