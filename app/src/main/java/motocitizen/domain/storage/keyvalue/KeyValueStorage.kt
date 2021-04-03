package motocitizen.domain.storage.keyvalue

interface KeyValueStorage<in T : Key> {

    fun putBoolean(key: T, value: Boolean)
    fun getBoolean(key: T, default: Boolean): Boolean

    fun putString(key: T, value: String?)
    fun getString(key: T, default: String? = null): String?

    fun putInt(key: T, value: Int)
    fun getInt(key: T, default: Int): Int

    fun putLong(key: T, value: Long)
    fun getLong(key: T, default: Long): Long

    fun putFloat(key: T, value: Float)
    fun getFloat(key: T, default: Float): Float

    fun clear()
}