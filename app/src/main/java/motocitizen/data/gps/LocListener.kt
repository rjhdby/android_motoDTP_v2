package motocitizen.data.gps

import android.location.Location
import android.location.LocationListener
import androidx.lifecycle.MutableLiveData
import com.google.type.LatLng


class LocListener :
    LocationListener {
    companion object {
        var currentLocation: MutableLiveData<LatLng> = MutableLiveData<LatLng>()
    }

    private var pointLD = MutableLiveData<LocationPoint>()
    fun setLiveData(liveData: MutableLiveData<LocationPoint>) {
        pointLD = liveData
    }

    override fun onLocationChanged(loc: Location) {
        pointLD.value = LocationPoint(loc.longitude, loc.latitude)
        currentLocation.value =
            LatLng.newBuilder().setLatitude(loc.latitude).setLongitude(loc.latitude).build()

    }
}