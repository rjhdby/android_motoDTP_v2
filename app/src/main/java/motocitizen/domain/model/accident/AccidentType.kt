package motocitizen.domain.model.accident

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import motocitizen.main.R

enum class AccidentType (val text: String, private val mapIcon: Int) {
    OTHER("Прочее",R.drawable.other),
    BREAK("Поломка", R.drawable.break_icon),
    STEAL("Угон",R.drawable.other),
    SOLO("Один участник",R.drawable.accident),
    MOTO_MOTO("Мот/мот",R.drawable.accident),
    MOTO_AUTO("Мот/авто",R.drawable.accident),
    MOTO_PEDESTRIAN("Наезд на пешехода",R.drawable.accident),
    SPAM("Спам",R.drawable.other);

    fun isAccident() = this in arrayOf(
        MOTO_AUTO,
        SOLO,
        MOTO_MOTO,
        MOTO_PEDESTRIAN
    )
    val icon: BitmapDescriptor
        get() = BitmapDescriptorFactory.fromResource(this.mapIcon)
}