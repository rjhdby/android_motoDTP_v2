package motocitizen.domain.model.accident

data class Address(
    val lat: Float,
    val lon: Float,
    val address: String,
) {
// todo донести из старого проекта
//    fun getGeoPoint() = GeoPoint(lat.toDouble(), lon.toDouble())
}