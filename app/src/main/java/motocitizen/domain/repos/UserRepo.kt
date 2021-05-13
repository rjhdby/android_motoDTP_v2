package motocitizen.domain.repos

import io.reactivex.Single
import motocitizen.data.network.user.User

interface UserRepo {
    fun getUser(skipCache: Boolean): Single<User>
}