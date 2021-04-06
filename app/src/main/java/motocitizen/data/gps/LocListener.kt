package motocitizen.data.gps

import android.location.Location
import android.location.LocationListener
import androidx.lifecycle.MutableLiveData

class LocListener :
    LocationListener {

    private var pointLD = MutableLiveData<LocationPoint>()
    fun setLivedata(livedata: MutableLiveData<LocationPoint>) {
        pointLD = livedata
    }


    override fun onLocationChanged(loc: Location) {
        pointLD.value = LocationPoint(loc.longitude, loc.latitude)

    }

}