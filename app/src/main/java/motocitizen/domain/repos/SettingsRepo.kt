package motocitizen.domain.repos

interface SettingsRepo {
    fun getDepth(): String
    fun getDistance(): String
    fun setDepth(depth: String)
    fun setDistance(distance: String)
}