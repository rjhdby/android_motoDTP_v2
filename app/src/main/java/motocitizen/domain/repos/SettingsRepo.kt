package motocitizen.domain.repos

interface SettingsRepo {
    fun getDepth(): String
    fun getDistance(): String
    fun setDepth(deep: String)
    fun setDistance(distance: String)
}