package motocitizen.data.repos

import io.reactivex.Single
import motocitizen.app.App
import motocitizen.data.converters.LoginConverter
import motocitizen.data.network.user.User
import motocitizen.data.network.user.UserApi
import motocitizen.data.storage.user.UserStorage
import motocitizen.domain.repos.UserRepo
import motocitizen.domain.utils.schedulersIoToMain

class UserDataRepo(
    private val api: UserApi,
    private val userStorage: UserStorage,
) : UserRepo {

    override fun getUser(skipCache: Boolean): Single<User> {
        return Single.defer {
            val user = userStorage.user
            if (skipCache || user == null) {
                api.getUser(App.authToken!!)
                    .map { LoginConverter.toUser(it) }
                    .doOnSuccess { userStorage.user = it }
            } else {
                Single.just(user)
            }
        }
            .schedulersIoToMain()
    }
}