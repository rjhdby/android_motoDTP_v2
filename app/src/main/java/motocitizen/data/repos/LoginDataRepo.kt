package motocitizen.data.repos

import io.reactivex.Completable
import motocitizen.data.converters.LoginConverter
import motocitizen.data.network.login.AuthData
import motocitizen.data.network.login.LoginApi
import motocitizen.data.storage.keyvalue.SharedPrefsKey
import motocitizen.data.storage.keyvalue.SharedPrefsStorage
import motocitizen.domain.model.login.SignIn
import motocitizen.domain.repos.LoginRepo
import motocitizen.domain.storage.ObjectTransformer
import motocitizen.domain.utils.schedulersIoToMain
import javax.inject.Inject

class LoginDataRepo @Inject constructor(
    private val loginApi: LoginApi,
    private val sharedPrefsStorage: SharedPrefsStorage,
    private val objectTransformer: ObjectTransformer,

    ) : LoginRepo {

    override fun login(data: SignIn): Completable {
        return loginApi.update(LoginConverter.toSignInRequest(data))
            .flatMapCompletable { response ->
                val userData = LoginConverter.fromSignInResponse(response.body())
                saveAuthData(userData)
            }
            .onErrorResumeNext { LoginConverter.fromSignInError(it) }
            .schedulersIoToMain()
    }

    private fun saveAuthData(userData: AuthData): Completable {
        return Completable.fromCallable {
            if (userData.authToken.isEmpty()) throw IllegalArgumentException("Token is empty!")
            val json = objectTransformer.toString(userData)
            sharedPrefsStorage.putString(SharedPrefsKey.AuthData, json)
        }
    }
}