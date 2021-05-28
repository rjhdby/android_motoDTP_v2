package motocitizen.data.repos

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import motocitizen.domain.repos.SettingsRepo
import motocitizen.main.R
import javax.inject.Inject

class SettingsDataRepo @Inject constructor(@ApplicationContext val context: Context) :
    SettingsRepo {
    private val reader = PreferenceManager.getDefaultSharedPreferences(context)
    private val writer = PreferenceManager.getDefaultSharedPreferences(context).edit()

    override fun getDeep(): String {
        return reader.getString(context.getString(R.string.settings_deep_key), null)!!
    }

    override fun getDistance(): String {
        return reader.getString(context.getString(R.string.settings_distance_key), null)!!
    }

    override fun setDeep(deep: String) {
        writer.putString(context.getString(R.string.settings_deep_key), deep).apply()
    }

    override fun setDistance(distance: String) {
        writer.putString(context.getString(R.string.settings_distance_key), distance).apply()
    }
}