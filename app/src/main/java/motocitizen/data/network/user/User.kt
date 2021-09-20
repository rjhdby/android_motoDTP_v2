package motocitizen.data.network.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import motocitizen.domain.model.user.UserRole

@Parcelize
data class User(
    val id: String,
    val nick: String,
    val role: UserRole,
) : Parcelable