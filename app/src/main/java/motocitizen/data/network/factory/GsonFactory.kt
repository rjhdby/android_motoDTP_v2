package motocitizen.data.network.factory

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import motocitizen.data.storage.DateTimeSerializer
import org.joda.time.DateTime

object GsonFactory {

    val networkGson: Gson by lazy {
        GsonBuilder()
            .create()
    }

    val appGson: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(DateTime::class.java, DateTimeSerializer())
            .create()
    }

}