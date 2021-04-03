package motocitizen.data.storage.keyvalue

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefsStorageImpl @Inject constructor(
    @ApplicationContext appContext: Context
) : SharedPrefsStorage {

    private val reader = PreferenceManager.getDefaultSharedPreferences(appContext)
    private val writer = PreferenceManager.getDefaultSharedPreferences(appContext).edit()

    override fun putBoolean(key: SharedPrefsKey, value: Boolean) = writer.putBoolean(key.name, value).apply()
    override fun getBoolean(key: SharedPrefsKey, default: Boolean) = reader.getBoolean(key.name, default)

    override fun putString(key: SharedPrefsKey, value: String?) = writer.putString(key.name, value).apply()
    override fun getString(key: SharedPrefsKey, default: String?): String? = reader.getString(key.name, default)

    override fun putInt(key: SharedPrefsKey, value: Int) = writer.putInt(key.name, value).apply()
    override fun getInt(key: SharedPrefsKey, default: Int) = reader.getInt(key.name, default)

    override fun putFloat(key: SharedPrefsKey, value: Float) = writer.putFloat(key.name, value).apply()
    override fun getFloat(key: SharedPrefsKey, default: Float) = reader.getFloat(key.name, default)

    override fun putLong(key: SharedPrefsKey, value: Long) = writer.putLong(key.name, value).apply()
    override fun getLong(key: SharedPrefsKey, default: Long) = reader.getLong(key.name, default)

    override fun clear() = writer.clear().apply()
}