package motocitizen.domain.repos

interface AuthRepo {
    fun saveToken(token: String?)
    fun getToken(): String?
    fun clearToken()
}