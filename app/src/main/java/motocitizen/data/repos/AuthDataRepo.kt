package motocitizen.data.repos

import motocitizen.data.storage.keyvalue.SharedPrefsKey
import motocitizen.data.storage.keyvalue.SharedPrefsStorageImpl
import motocitizen.domain.repos.AuthRepo
import javax.inject.Inject

class AuthDataRepo @Inject constructor(
    private val sharedPrefsStorageImpl: SharedPrefsStorageImpl
) : AuthRepo {
    override fun saveToken(token: String?) {
        sharedPrefsStorageImpl.putString(SharedPrefsKey.AuthToken, token)
    }

    override fun getToken(): String? {
        return sharedPrefsStorageImpl.getString(SharedPrefsKey.AuthToken, null)
    }
}