package motocitizen.domain.providers

interface UserDataProvider {
    fun getAuthToken(): String?
}