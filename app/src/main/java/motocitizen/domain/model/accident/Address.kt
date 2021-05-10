package motocitizen.domain.model.accident

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var lat: Double,
    var lon: Double,
    var address: String
) : Parcelable
