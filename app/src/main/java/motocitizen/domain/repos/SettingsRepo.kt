package motocitizen.domain.repos

interface SettingsRepo {
    fun getDeep(): String
    fun getDistance(): String
    fun setDeep(deep: String)
    fun setDistance(distance: String)
}