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

    override fun getDepth(): String {
        return reader.getString(
            context.getString(R.string.settings_depth_key),
            context.getString(R.string.depth_default_value)
        )!!
    }

    override fun getDistance(): String {
        return reader.getString(
            context.getString(R.string.settings_distance_key),
            context.getString(R.string.radius_default_value)
        )!!
    }

    override fun setDepth(depth: String) {
        writer.putString(context.getString(R.string.settings_depth_key), depth).apply()
    }

    override fun setDistance(distance: String) {
        writer.putString(context.getString(R.string.settings_distance_key), distance).apply()
    }
}